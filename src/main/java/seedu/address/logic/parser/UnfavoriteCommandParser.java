package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.logic.commands.UnfavoriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Favorite;

import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class UnfavoriteCommandParser implements Parser<UnfavoriteCommand> {
    @Override
    public UnfavoriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfavoriteCommand(index,false);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavoriteCommand.MESSAGE_USAGE));
        }
    }
}
