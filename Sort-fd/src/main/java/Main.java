import argsToOptionParser.ArgsParser;
import argsToOptionParser.ArgsParserImpl;
import argsToOptionParser.SortParameters;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sortFileData.FileMergeSorter;

public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        ArgsParser argsParser = ArgsParserImpl.getInstance();

        try {
            SortParameters sortParameters = argsParser.parseInputArgs(args);
            FileMergeSorter fileMergeSorter = new FileMergeSorter();
            fileMergeSorter.sort(sortParameters);
            System.out.println("Operations done. You can see result at file: " + sortParameters.getOutputFileName());
        } catch (IllegalArgumentException | ParseException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }
}