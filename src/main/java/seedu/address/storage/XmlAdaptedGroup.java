package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedGroup {

    @XmlValue
    private String GroupName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        GroupName = source.groupName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        return new Group(GroupName);
    }

}
