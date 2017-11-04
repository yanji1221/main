package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.model.group.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class GroupCommandParser implements Parser<GroupCommand> {
    @Override
    public GroupCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                System.out.println("Caught you!!!");
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
            }
            String[] indices = trimmedArgs.split("\\s+");
            List<String> inputInString = Arrays.asList(indices);

            List<Index> input = new ArrayList<Index>();

            for (String ind: inputInString) {
                Index index = ParserUtil.parseIndex(ind);
                input.add(index);
            }

            return new GroupCommand(new Group(args),input);
        }
        catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }
    }
}
