package ArgsToOptionParser.Enum;

public enum OptionName {

    SORT_MODE_ASCEND("a"),
    SORT_MODE_DESCEND("d"),
    SORT_DATA_TYPE_STRING("s"),
    SORT_DATA_TYPE_INTEGER("i"),
    OUTPUT_FILENAME("o");

    private final String optionName;

    OptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }
}
