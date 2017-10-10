package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds any persons whose tags contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    public static final String MESSAGE_ARGUMENTS = "Keywords entered are: ";
    private final String[] arguments;

    public FindTagCommand(String[] str) {
        requireNonNull(str);
        this.arguments = str;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_ARGUMENTS + String.join(" ", arguments));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTagCommand)) {
            return false;
        }

        // state check
        FindTagCommand e = (FindTagCommand) other;
        return Arrays.deepEquals(arguments, e.arguments);
    }
}
