package ArgsToOptionParser;

import ArgsToOptionParser.Enum.SortDataType;
import ArgsToOptionParser.Enum.SortMode;

import java.util.List;

public class SortParameters {

    private SortMode sortMode = SortMode.ASCEND;
    private SortDataType sortDataType;
    private String outputFileName;
    private List<String> inputFileNames;

    public SortMode getSortMode() {
        return sortMode;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }

    public SortDataType getSortDataType() {
        return sortDataType;
    }

    public void setSortDataType(SortDataType sortDataType) {
        this.sortDataType = sortDataType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public List<String> getInputFileNames() {
        return inputFileNames;
    }

    public void setInputFileNames(List<String> inputFileNames) {
        this.inputFileNames = inputFileNames;
    }
}
