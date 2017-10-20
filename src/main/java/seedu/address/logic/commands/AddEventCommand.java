package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Add an event to the application
 */

public class AddEventCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an event with a name, date and description. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Doctor appointment "
            + PREFIX_DATE + "27/10/2017 "
            + PREFIX_DESCRIPTION + "Come before 9am";

    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Date: %2$s, Description: %3$s";

    private final String name;
    private final String date;
    private final String description;

    public AddEventCommand(String name, String date, String description) {
        requireNonNull(name);
        requireNonNull(date);

        this.name = name;
        this.date = date;
        this.description = description;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, name, date, description));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        // state check
        AddEventCommand e = (AddEventCommand) other;
        return name.equals(e.name) && date.equals(e.date) && description.equals(e.description);
    }
}
