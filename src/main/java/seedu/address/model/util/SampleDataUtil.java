package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                    //@@author yanji1221
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Birthday("1993/11/12"), new Address("Blk 30 Geylang Street 29, #06-40"), new ProfilePage("www.facebook.com"),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Birthday("1988/12/22"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new ProfilePage("www.facebook.com"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Birthday("1987/12/12"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new ProfilePage("www.facebook.com"),
                    getTagSet("neighbours")),
                    //@@author
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Birthday("1999/01/01"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new ProfilePage("www.facebook.com"),
                    getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Birthday("1985/03/04"), new Address("Blk 47 Tampines Street 20, #17-35"), new ProfilePage("www.facebook.com"),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Birthday("1983/05/08"), new Address("Blk 45 Aljunied Street 85, #11-31"),new ProfilePage("www.facebook.com"),
                    getTagSet("colleagues"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
