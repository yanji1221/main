package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the phones given.
 */

public class FavoritePredicate implements Predicate<ReadOnlyPerson> {

    public FavoritePredicate() { }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavorite().value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoritePredicate // instanceof handles nulls
        ); // state check
    }

}