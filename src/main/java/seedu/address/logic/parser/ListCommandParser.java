package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.SortUtil.setupArguments;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand(null);
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> dataKeywords = new ArrayList<>();
        List<SortArgument> sortArgumentList = new ArrayList<>();

        setupArguments(keywords, dataKeywords, sortArgumentList, ListCommand.MESSAGE_USAGE);

        if (!dataKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(sortArgumentList);
    }

}
