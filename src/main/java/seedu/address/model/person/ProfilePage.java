package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProfilePage(String)}
 */
public class ProfilePage {

    public static final String MESSAGE_PROFILEPAGE_CONSTRAINTS =
            "Person Profile page should be a valid URL pointing to that person's profile";
    public static final String PROFILEPAGE_VALIDATION_REGEX = "^(https?:\\/\\/)?(www\\.)?([\\w]+\\.)+[‌​\\w]{2,63}\\/?$";

    public final String value;

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
        if(!profileLink.endsWith("/")) {
            this.value = profileLink +"/";
        } else {
            this.value = profileLink;
        }
    }

    /**
     * Returns if a given string is a valid person profile page.
     */
    public static boolean isValidProfilePage(String test) {
        if(test.equals("")) {
            return true;
        }
        return test.matches(PROFILEPAGE_VALIDATION_REGEX);
    }

    /**
     * Returns true if this person has a profile page.
     */
    public boolean hasProfilePage(){
        return (this.value.equals("") || this.value == null) ? false: true;
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
