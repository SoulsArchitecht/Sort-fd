package argsToOptionParser.Enum;

public enum OptionName {

    SORT_MODE_ASCEND("a"),
    SORT_MODE_DESCEND("d"),
    SORT_DATA_TYPE_STRING("s"),
    SORT_DATA_TYPE_INTEGER("i");

    private final String optionName;

    OptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionName() {
        return optionName;
    }
}
