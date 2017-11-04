package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grouping several people together\n" +
            "Parameters: " + "GROUP_NAME " + "INDEX_1 " + "INDEX_2 " + "...\n" +
            "Example: " + COMMAND_WORD +" Stars " +"1 " + "3 ";

    public static final String MESSAGE_SUCCESS = "Success Grouping!";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group name already exists in the address book";

    private Group toAdd;

    private final List<Index> listTargetIndices;

    public GroupCommand(Group group,List<Index> listTargetIndices) throws IllegalValueException {
        toAdd = new Group(group);
        this.listTargetIndices = listTargetIndices;
    }

    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex : listTargetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        List<ReadOnlyPerson> listPersonsToGroup = new ArrayList<ReadOnlyPerson>();

        for (Index targetIndex : listTargetIndices) {
            ReadOnlyPerson personToGroup = lastShownList.get(targetIndex.getZeroBased());
            listPersonsToGroup.add(personToGroup);
        }

        requireNonNull(toAdd);
            for (ReadOnlyPerson personToGroup : listPersonsToGroup) {
                toAdd.addPerson(personToGroup);
               // System.out.println("haha");
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, listPersonsToGroup));
    }
}
