//@@author yanji1221
package seedu.address.ui;

import java.lang.String;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class ComingBirthdayListPanel extends UiPart<Region> {
    private static final String FXML = "ComingBirthdayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> comingBirthdayListView;

    public ComingBirthdayListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        ObservableList<ReadOnlyPerson> comingBirthdayList = comingBirthdayListGetter(personList);
        setConnections(comingBirthdayList);
    }

    private ObservableList<ReadOnlyPerson> comingBirthdayListGetter(ObservableList<ReadOnlyPerson> personList) {
        List<ReadOnlyPerson> comingBirthdayList = personList.stream().collect(Collectors.toList());
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        month += 1;

        for (int i = 0; i < personList.size(); i++) {
            if (!personList.get(i).getBirthday().toString()
                    .substring(5, 7).equals(Integer.toString(month))) {
                comingBirthdayList.remove(i);
            }
        }

        return FXCollections.observableArrayList(comingBirthdayList);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> comingBirthdayList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                comingBirthdayList, (person) -> new PersonCard(person, comingBirthdayList.indexOf(person) + 1));
        comingBirthdayListView.setItems(mappedList);
        comingBirthdayListView.setCellFactory(listView -> new BirthdayListViewCell());
    }


    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            comingBirthdayListView.scrollTo(index);
            comingBirthdayListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class BirthdayListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard comingBirthday, boolean empty) {
            super.updateItem(comingBirthday, empty);

            if (empty || comingBirthday == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(comingBirthday.getRoot());
            }
        }
    }

}
