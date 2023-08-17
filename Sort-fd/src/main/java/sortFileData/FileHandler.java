package sortFileData;

import exception.DataTransformErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stringTransform.Transformer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class FileHandler <T> {

    private final static Logger logger = LoggerFactory.getLogger(FileHandler.class);
    private final String fileName;
    private final BufferedReader bufferedReader;
    private final Transformer<String, T> dataTransformer;
    private final Comparator<T> comparator;
    private boolean reachedEOF = false;
    private long currentLineNumber = 0;
    private T previousElement = null;
    private T currentElement;

    public FileHandler(String fileName, Transformer<String, T> dataTransformer, Comparator<T> comparator) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        this.fileName = fileName;
        this.dataTransformer = dataTransformer;
        this.comparator = comparator;
        this.currentElement = getNextElementFromFile();
        while (this.currentElement == null) {
            if (reachedEOF) {
                break;
            }
            this.currentElement = getNextElementFromFile();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isReachedEOF() {
        return reachedEOF;
    }

    public long getCurrentLineNumber() {
        return currentLineNumber;
    }

    public T getPreviousElement() {
        return previousElement;
    }

    public T getCurrentElement() {
        return currentElement;
    }

    private T getNextElementFromFile() throws IOException {
        if (reachedEOF) {
            return null;
        }

        String nextLine = bufferedReader.readLine();
        ++currentLineNumber;

        if (nextLine == null) {
            bufferedReader.close();
            previousElement = currentElement;
            currentElement = null;
            reachedEOF = true;
            return null;
        }

        if (nextLine.isEmpty()) {
            logger.warn("Empty line found in file " + fileName + " in line " + currentLineNumber +
                    ".Line was skipped");
            return null;
        }

        if (nextLine.contains(" ")) {
            logger.warn("Spaces was found in file " + fileName + " in line " + currentLineNumber +
                    ". Data element was skipped");
            return null;
        }

        T value = null;
        try {
            value = dataTransformer.transform(nextLine);
        } catch (DataTransformErrorException e) {
            logger.error("Next error in file " + fileName + " in line" + currentLineNumber
                + ": " + e.getMessage());
            return null;
        }

        return value;
    }

    private T findNextNotNullElement() throws IOException {
        T nextElement = getNextElementFromFile();
        while (nextElement == null) {
            if (reachedEOF) {
                return null;
            }
            nextElement = getNextElementFromFile();
        }
        return nextElement;
    }

    public T nextElement() throws IOException {
        T nextElement = findNextNotNullElement();
        if (nextElement == null) {
            return null;
        }

        while (comparator.compare(currentElement, nextElement) < 0 && nextElement != null) {
            logger.error("File " + fileName + " not sorted in line " + currentElement
                + ". Data element was skipped");
            nextElement = findNextNotNullElement();
        }

        if (nextElement == null) {
            return null;
        }

        previousElement = currentElement;
        currentElement = nextElement;
        return currentElement;
    }
}
