package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.*;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Add an event to the application
 */

public class AddEventCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an event with a name, description and timestamp. \n"
            + "Parameters: "
            + PREFIX_EVENT + "NAME "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT + "Doctor appointment "
            + PREFIX_DATE + "27-10-17 "
            + PREFIX_DESCRIPTION + "Come before 9am";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "AddEvent command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
