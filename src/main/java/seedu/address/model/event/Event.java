package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Name;

public class Event {
    /**
     * Reuse name class of person cause they have the exact behavior for now
     */
    private ObjectProperty<Name> name;
    private ObjectProperty<Dates> date;
    private String description;

    public Event(Name name, Dates date, String description) {
        requireAllNonNull(name, date, description);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.description = description;

    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    public Name getName() {
        return name.get();
    }

    public void setDate(Dates date) {
        this.date.set(requireNonNull(date));
    }

    public ObjectProperty<Dates> dateProperty() { return date; }

    public Dates getDate() {
        return date.get();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        // state check
        Event e = (Event) other;
        return name.equals(e.name) && date.equals(e.date) && description.equals(e.description);
    }
}
