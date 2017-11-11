//@@author hxy0229
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FavoriteCommandParser implements Parser<FavoriteCommand> {
    @Override
    public FavoriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavoriteCommand(index, true);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavoriteCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
