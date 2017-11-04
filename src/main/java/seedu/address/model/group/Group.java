package seedu.address.model.group;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS = "Groups names should be alphanumeric";
    public static final String GROUP_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String groupName;

    private ArrayList<ReadOnlyPerson> persons;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Group(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidGroupName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.groupName = trimmedName;
    }

    public Group(Group g) throws IllegalValueException {
        this(g.getName());
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    public void addPerson(ReadOnlyPerson person){
            persons.add(person);
    }

    public String getName(){
        return this.groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.equals(((Group) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + groupName + ']';
    }

}