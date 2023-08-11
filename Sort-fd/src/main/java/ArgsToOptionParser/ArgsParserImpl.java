package ArgsToOptionParser;

import ArgsToOptionParser.Enum.OptionName;
import ArgsToOptionParser.Enum.SortDataType;
import ArgsToOptionParser.Enum.SortMode;
import org.apache.commons.cli.*;

public class ArgsParserImpl implements ArgsParser {

    private static ArgsParser argsParser;
    private final Options options;

    private ArgsParserImpl() {
        options = new Options();
        options.addOption(OptionName.SORT_MODE_ASCEND.getOptionName(),
                false, "Set ascending mode for sort [Default: ASCENDING");
        options.addOption(OptionName.SORT_MODE_DESCEND.getOptionName(),
                false, "Set descending mode for sort [Default: ASCENDING");
        options.addOption(OptionName.SORT_DATA_TYPE_STRING.getOptionName(),
                true, "Set sort data type String");
        options.addOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName(),
                true,"Set sort data type Integer");
        options.addOption(OptionName.OUTPUT_FILENAME.getOptionName(),
                true, "Set output file name");
    }

    public static ArgsParser getInstance() {
        if (argsParser == null) {
            synchronized (ArgsParser.class) {
                if (argsParser == null) {
                    argsParser = new ArgsParserImpl();
                }
            }
        }
        return argsParser;
    }

    private SortParameters buildSortParameters(CommandLine parsedArgs) {
        SortParameters sortParameters = new SortParameters();

        if (parsedArgs.hasOption(OptionName.SORT_MODE_DESCEND.getOptionName())) {
            sortParameters.setSortMode(SortMode.DESCEND);
        } else {
            sortParameters.setSortMode(SortMode.DESCEND);
        }

        if (parsedArgs.hasOption(OptionName.SORT_DATA_TYPE_STRING.getOptionName())) {
            sortParameters.setSortDataType(SortDataType.STRING);
        } else {
            sortParameters.setSortDataType(SortDataType.INTEGER);
        }

        if (parsedArgs.hasOption(OptionName.OUTPUT_FILENAME.getOptionName())) {
            sortParameters.setOutputFileName(parsedArgs.getOptionValue(OptionName.OUTPUT_FILENAME.getOptionName()));
        } else {
            throw new IllegalArgumentException("Output file param required");
        }

        sortParameters.setInputFileNames(parsedArgs.getArgList());

        return sortParameters;
    }

    private void validateParsedArgs(CommandLine parsedArgs) {
        if (parsedArgs.hasOption(OptionName.SORT_MODE_ASCEND.getOptionName()) && parsedArgs.hasOption
                (OptionName.SORT_MODE_DESCEND.getOptionName())) {
            throw new IllegalArgumentException("Redefined sort mode option");
        }
        if (parsedArgs.hasOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName()) == parsedArgs.hasOption
                (OptionName.SORT_DATA_TYPE_STRING.getOptionName())) {
            throw new IllegalArgumentException("Redefined or not specified sort data type option");
        }
        if (parsedArgs.getArgs().length < 1) {
            throw new IllegalArgumentException("No input files for sort data");
        }
    }

    @Override
    public SortParameters parseInputArgs(String[] args) throws ParseException, IllegalArgumentException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine parsedArgs = commandLineParser.parse(options, args);
        validateParsedArgs(parsedArgs);
        return buildSortParameters(parsedArgs);
    }

}
