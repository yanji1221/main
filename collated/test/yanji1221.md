# yanji1221
###### \java\guitests\guihandles\ComingBirthdayListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.PersonCard;

/**
 * Provides a handle for {@code ComingBirthdayListPanel} containing the list of {@code PersonCard}.
 */
public class ComingBirthdayListPanelHandle extends NodeHandle<ListView<PersonCard>> {
    public static final String COMING_BIRTHDAY_LIST_VIEW_ID = "#comingBirthdayListView";

    private Optional<PersonCard> lastRememberedSelectedPersonCard;

    public ComingBirthdayListPanelHandle(ListView<PersonCard> comingBirthdayListPanelNode) {
        super(comingBirthdayListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<PersonCard> comingBirthdayList = getRootNode().getSelectionModel().getSelectedItems();

        if (comingBirthdayList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new PersonCardHandle(comingBirthdayList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<PersonCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) {
        List<PersonCard> cards = getRootNode().getItems();
        Optional<PersonCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code PersonCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<PersonCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<PersonCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getBirthday() {
        return birthdayLabel.getText();
    }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_BIRTHDAY_AMY = "1988/08/18";
    public static final String VALID_BIRTHDAY_BOB = "1992/01/01";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "12/12"; // year is missing
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withAddress(VALID_ADDRESS_AMY).withProfilePage(VALID_PROFILE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withAddress(VALID_ADDRESS_BOB).withProfilePage(VALID_PROFILE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }
```
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
        // different birthday -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withAddress(VALID_BIRTHDAY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + ADDRESS_DESC_BOB + PROFILE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + ADDRESS_DESC_BOB + PROFILE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + BIRTHDAY_DESC_BOB + ADDRESS_DESC_BOB + PROFILE_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_BIRTHDAY_DESC + ADDRESS_DESC_BOB + PROFILE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + BIRTHDAY_DESC_BOB + INVALID_ADDRESS_DESC + PROFILE_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);
        /*
        // invalid profile page
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + BIRTHDAY_DESC_BOB + ADDRESS_DESC_BOB + INVALID_PROFILE_DESC + TAG_DESC_HUSBAND
                         + TAG_DESC_FRIEND, ProfilePage.MESSAGE_PROFILEPAGE_CONSTRAINTS); */

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + BIRTHDAY_DESC_BOB + ADDRESS_DESC_BOB + PROFILE_DESC_BOB  + INVALID_TAG_DESC
                        + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + INVALID_ADDRESS_DESC
                        + PROFILE_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + BIRTHDAY_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withBirthday(VALID_BIRTHDAY_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String INVALID_BIRTHDAY = "adsd";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_BIRTHDAY = "1989/12/11";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null);
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY));

        assertEquals(expectedBirthday, actualBirthday.get());
    }
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("^")); // only non-alphanumeric characters
        assertFalse(Birthday.isValidBirthday("1993")); // year only
        assertFalse(Birthday.isValidBirthday("19920102")); // not in the format yyyy/mm/dd
        assertFalse(Birthday.isValidBirthday("1992-01-02")); // wrong symovol '-'
        assertFalse(Birthday.isValidBirthday("1992/1/02")); // month must be two digits
        assertFalse(Birthday.isValidBirthday("1992/01/2")); // date must be two digits
        assertFalse(Birthday.isValidBirthday("01/02/1993")); // wrong order
        assertFalse(Birthday.isValidBirthday("92/01/02")); // year must be four digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("1993/12/12")); // int the format yyyy/mm/dd
        assertTrue(Birthday.isValidBirthday("1993/02/30")); // possible
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
        descriptor.setBirthday(person.getBirthday());
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_BIRTHDAY = "1988/08/18";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Favorite} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavorite(boolean favorite) {
        this.person.setFavorite(new Favorite(favorite));
        return this;
    }

    public Person build() {
        return this.person;
    }
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withBirthday("1988/08/18").withPhone("97235255").withProfilePage("www.facebook.com").withFavorite(false)
            .withTags("friends").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withBirthday("1980/03/12").withPhone("85357654").withFavorite(false)
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withBirthday("1999/12/11").withFavorite(false)
            .withAddress("wall street").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("85352533")
            .withEmail("cornelia@example.com").withBirthday("1980/03/01").withFavorite(false)
            .withAddress("10th street").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withBirthday("1990/01/01").withFavorite(false)
            .withAddress("michegan ave").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withBirthday("1982/06/05").withFavorite(false)
            .withAddress("little tokyo").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withBirthday("2000/09/09").withFavorite(false)
            .withAddress("4th street").build();
```
###### \java\seedu\address\ui\ComingBirthdayListPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ComingBirthdayListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class ComingBirthdayListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private ComingBirthdayListPanelHandle comingBirthdayListPanelHandle;

    private boolean isMoreThanOne = false;

    @Before
    public void setUp() {
        ComingBirthdayListPanel comingBirthdayListPanel = new ComingBirthdayListPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(comingBirthdayListPanel);

        comingBirthdayListPanelHandle = new ComingBirthdayListPanelHandle(getChildNode(comingBirthdayListPanel
                        .getRoot(),
                ComingBirthdayListPanelHandle.COMING_BIRTHDAY_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        List<ReadOnlyPerson> comingBirthdayList = TYPICAL_PERSONS.stream().collect(Collectors.toList());
        boolean isRemoved = false;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        if (((month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) && date == 31)
                || ((month == 4 || month == 6 || month == 9
                || month == 11) && date == 30)
                || (month == 2 && date == 29)) {
            month += 1;
            if (month == 13) {
                month = 1;
            }
            date = 0;
        }
        for (int i = 0; i < comingBirthdayList.size(); i++) {
            if (!(Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            } else if ((Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)
                    && Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(8)) < date) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            }
            if (isRemoved) {
                i--;
                isRemoved = false;
            }
        }

        if (comingBirthdayList.size() > 1) {
            isMoreThanOne = true;
        }

        for (int i = 0; i < comingBirthdayList.size(); i++) {
            comingBirthdayListPanelHandle.navigateToCard(comingBirthdayList.get(i));
            ReadOnlyPerson expectedPerson = comingBirthdayList.get(i);
            PersonCardHandle actualCard = comingBirthdayListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        if (isMoreThanOne) {
            PersonCardHandle expectedCard = comingBirthdayListPanelHandle
                    .getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
            PersonCardHandle selectedCard = comingBirthdayListPanelHandle.getHandleToSelectedCard();
            assertCardEquals(expectedCard, selectedCard);
        }
    }
}
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub", true);
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    private static final int numberOfPerson = EVENT_STUB.data.getPersonList().size();
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyPerson toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + BIRTHDAY_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + PROFILE_DESC_AMY
                + "   " + TAG_DESC_FRIEND;

        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY +  PROFILE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY +  PROFILE_DESC_AMY + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the address book except name -> acccepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
                .withFavorite(false).withProfilePage(VALID_PROFILE_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY +  PROFILE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        /* Case: add a person with all fields same as another person in the address book except phone -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_AMY).withFavorite(false)
                .withProfilePage(VALID_PROFILE_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY +  PROFILE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        /* Case: add a person with all fields same as another person in the address book except email -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_AMY).withProfilePage(VALID_PROFILE_AMY)
                .withTags(VALID_TAG_FRIEND).withFavorite(false).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        /* Case: add a person with all fields same as another person in the address book except birthday -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBirthday(VALID_BIRTHDAY_BOB).withAddress(VALID_ADDRESS_AMY).withProfilePage(VALID_PROFILE_AMY)
                .withFavorite(false).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_BOB
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY  + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        /* Case: add a person with all fields same as another person in the address book except address -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_BOB).withProfilePage(VALID_PROFILE_AMY)
                .withFavorite(false).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_BOB + PROFILE_DESC_AMY  + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        /* Case: add a person with all fields same as another person in the address book except profile -> rejected */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withAddress(VALID_ADDRESS_AMY).withProfilePage(VALID_PROFILE_BOB)
                .withFavorite(false).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);


        /* Case: filters the person list before adding -> added */
        executeCommand(FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredPersonList().size()
                < getModel().getAddressBook().getPersonList().size();
        assertCommandSuccess(IDA);


        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(ALICE);


        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PROFILE_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + NAME_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().isAnyCardSelected();
        assertCommandSuccess(CARL);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_NO_NAME_FORMAT, AddCommand.MESSAGE_USAGE));


        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + INVALID_EMAIL_DESC + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_BIRTHDAY_DESC + ADDRESS_DESC_AMY + PROFILE_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + INVALID_ADDRESS_DESC + PROFILE_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid profile -> rejected
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + BIRTHDAY_DESC_AMY + ADDRESS_DESC_AMY + INVALID_PROFILE_DESC;
        assertCommandFailure(command, ProfilePage.MESSAGE_PROFILEPAGE_CONSTRAINTS); */

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + BIRTHDAY_DESC_AMY
                + ADDRESS_DESC_AMY + PROFILE_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_BIRTHDAY_DESC,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
