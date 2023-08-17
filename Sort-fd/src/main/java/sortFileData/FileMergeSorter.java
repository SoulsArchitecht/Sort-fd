package sortFileData;

import argsToOptionParser.Enum.SortMode;
import argsToOptionParser.SortParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stringTransform.StringToIntegerTransformer;
import stringTransform.StringToStringTransformer;
import stringTransform.Transformer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileMergeSorter implements Sort {

    private final static Logger logger = LoggerFactory.getLogger(FileMergeSorter.class);

    private <T> void mergeSort(String outputFileName, List<String> inputFileNames,
                               Comparator<T> comparator, Transformer<String, T> transformer) {

        List<FileHandler<T>> fileHandlers = new ArrayList<>();

        for (String fileName : inputFileNames) {
            try {
                fileHandlers.add(new FileHandler<>(fileName, transformer, comparator));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        fileHandlers.removeIf(fileHandler -> fileHandler.getCurrentElement() == null);

        try (PrintWriter outputFile = new PrintWriter(outputFileName)) {
            Path outputFilePath = Paths.get(outputFileName);
            if (!Files.exists(outputFilePath)) {
                Files.createFile(outputFilePath);
            }

            while (fileHandlers.size() > 0) {
                int fileHandlerNextElementIndex = 0;
                T searchedElement = fileHandlers.get(0).getCurrentElement();
                for (int i = 0; i < fileHandlers.size(); ++i) {
                    T currentElement = fileHandlers.get(i).getCurrentElement();
                    if (comparator.compare(currentElement, searchedElement) >= 0) {
                        fileHandlerNextElementIndex = i;
                        searchedElement = currentElement;
                    }
                }

                outputFile.println(searchedElement);

                try {
                    var fileHandler = fileHandlers.get(fileHandlerNextElementIndex);
                    T nextElement = fileHandler.nextElement();
                    if (nextElement == null) {
                        fileHandlers.remove(fileHandlerNextElementIndex);
                    }
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                    fileHandlers.remove(fileHandlerNextElementIndex);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sort(SortParameters sortParameters) {

        switch (sortParameters.getSortDataType()) {
            case INTEGER -> {
                Comparator<Integer> comparator = sortParameters.getSortMode() == SortMode.ASCEND
                        ? new IntegerComparator() : new IntegerComparator().reversed();
                mergeSort(sortParameters.getOutputFileName(), sortParameters.getInputFileNames(), comparator,
                    new StringToIntegerTransformer());
            }
            case STRING -> {
                Comparator<String> comparator = sortParameters.getSortMode() == SortMode.ASCEND
                        ? new StringComparator() : new StringComparator().reversed();
                mergeSort(sortParameters.getOutputFileName(), sortParameters.getInputFileNames(), comparator,
                        new StringToStringTransformer());
            }
            default -> throw new IllegalArgumentException("Unknown file data type");
        }
    }
}
