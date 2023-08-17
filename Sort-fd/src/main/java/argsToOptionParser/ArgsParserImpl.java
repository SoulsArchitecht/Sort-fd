package argsToOptionParser;

import argsToOptionParser.Enum.OptionName;
import argsToOptionParser.Enum.SortDataType;
import argsToOptionParser.Enum.SortMode;
import org.apache.commons.cli.*;

import java.util.List;

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
                false, "Set sort data type String");
        options.addOption(OptionName.SORT_DATA_TYPE_INTEGER.getOptionName(),
                false,"Set sort data type Integer");
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
            sortParameters.setSortMode(SortMode.ASCEND);
        }

        if (parsedArgs.hasOption(OptionName.SORT_DATA_TYPE_STRING.getOptionName())) {
            sortParameters.setSortDataType(SortDataType.STRING);
        } else {
            sortParameters.setSortDataType(SortDataType.INTEGER);
        }

        sortParameters.setOutputFileName(parsedArgs.getArgList().get(0));
        sortParameters.setInputFileNames(inputFileList(parsedArgs));

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
        if (parsedArgs.getArgs().length < 2) {
            throw new IllegalArgumentException("No output or input files for sort data");
        }
    }

    @Override
    public SortParameters parseInputArgs(String[] args) throws ParseException, IllegalArgumentException {
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine parsedArgs = commandLineParser.parse(options, args);
        validateParsedArgs(parsedArgs);
        return buildSortParameters(parsedArgs);
    }

    List<String> inputFileList(CommandLine parsedArgs) {
        List<String> outputFilesList = parsedArgs.getArgList();
        if (outputFilesList.size() > 2) {
            outputFilesList.remove(0);
        } else {
            throw new IllegalArgumentException("No output or input files for sort data");
        }
        return outputFilesList;
    }

}
