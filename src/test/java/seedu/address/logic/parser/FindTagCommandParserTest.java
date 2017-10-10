package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindTagCommand;

public class FindTagCommandParserTest {
    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindCommand =
                new FindTagCommand(new String[]{"friend","family"});
        assertParseSuccess(parser, "friend family", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t family  \t", expectedFindCommand);
    }
}
