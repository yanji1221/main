package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Email;
import seedu.address.model.person.ProfilePage;
import seedu.address.model.person.Name;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;


/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String address;
    //@@author quangtdn
    @XmlElement(required = false)
    private String profile = "";
    //@@author
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedGroup> grouped = new ArrayList<>();
    @XmlElement
    private boolean favorite = false;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        //@@author yanji1221
        birthday = source.getBirthday().value;
        //@@author
        address = source.getAddress().value;

        if (!source.getProfilePage().value.equals("")) {
            profile = source.getProfilePage().value;
        }

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        grouped = new ArrayList<>();
        for (Group group : source.getGroups()) {
            grouped.add(new XmlAdaptedGroup(group));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final List<Group> personGroups = new ArrayList<>();
        for (XmlAdaptedGroup group : grouped) {
            personGroups.add(group.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Birthday birthday = new Birthday(this.birthday);
        final Address address = new Address(this.address);
        final ProfilePage profile = new ProfilePage(this.profile);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Group> groups = new HashSet<>(personGroups);
        final Favorite favorite = new Favorite(this.favorite);
        return new Person(name, phone, email, birthday, address, profile, favorite, tags, groups);
    }
}
