package argsToOptionParser;

import org.apache.commons.cli.ParseException;

public interface ArgsParser {

    SortParameters parseInputArgs(String[] args) throws ParseException, IllegalArgumentException;
}
