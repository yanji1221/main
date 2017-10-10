package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.FindTagCommand.MESSAGE_ARGUMENTS;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class FindTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String[] tags = new String[]{"some","tag"};
        assertCommandFailure(prepareCommand(tags), model, MESSAGE_ARGUMENTS + String.join(" ", tags));
    }

    @Test
    public void equals() {
        final FindTagCommand standardCommand = new FindTagCommand(new String[]{VALID_TAG_HUSBAND,VALID_TAG_FRIEND});

        // same values -> returns true
        FindTagCommand  commandWithSameValues = new FindTagCommand(new String[]{VALID_TAG_HUSBAND,VALID_TAG_FRIEND});
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different tags -> returns false
        assertFalse(standardCommand.equals(new FindTagCommand(new String[]{"hus",VALID_TAG_FRIEND})));

        // different tags -> returns false
        assertFalse(standardCommand.equals(new FindTagCommand(new String[]{VALID_TAG_HUSBAND,"frien"})));

        // different number of tags  -> returns false
        assertFalse(standardCommand.equals(new FindTagCommand(new String[]{VALID_TAG_HUSBAND})));
    }

    /**
     * Parses {@code userInput} into a {@code FindTagCommand}.
     */
    private FindTagCommand prepareCommand(String[] tags) {
        FindTagCommand findTagCommand = new FindTagCommand(tags);
        findTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return findTagCommand;
    }
}
