//@@author quangtdn
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile page in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProfilePage(String)}
 */
public class ProfilePage {

    public static final String MESSAGE_PROFILEPAGE_CONSTRAINTS =
            "Person Profile page should be a valid URL pointing to that person's profile";
    public static final String PROFILEPAGE_VALIDATION_REGEX = "^(https?:\\/\\/)?(www\\.)?([\\w]+\\.)+[‌​\\w]{2,63}\\/?$";
    //public static final String PROFILEPAGE_VALIDATION_REGEX = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";

    public final String value;

    public boolean hasProfilePage(){
        return (!value.equals("www.unknown.com"));
    }

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday address string is invalid.
     */
    public ProfilePage(String profile) throws IllegalValueException {
        requireNonNull(profile);
        if (!isValidProfilePage(profile)) {
            throw new IllegalValueException(MESSAGE_PROFILEPAGE_CONSTRAINTS);
        }
        String profileLink = profile.replace("https://", "");
        if(!profileLink.equals("") && !profileLink.endsWith("/")) {
            this.value = profileLink +"/";
        } else {
            this.value = profileLink;
        }
    }

    public ProfilePage() throws IllegalValueException {
        //requireNonNull(profile);
        this.value = "www.unknown.com";
        /*if (!isValidProfilePage(this.value)) {
            throw new IllegalValueException(MESSAGE_PROFILEPAGE_CONSTRAINTS);
        }*/

    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidProfilePage(String test) {
        return test.matches(PROFILEPAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePage // instanceof handles nulls
                && this.value.equals(((ProfilePage) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
//@@author