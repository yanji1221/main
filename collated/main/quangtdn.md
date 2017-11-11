# quangtdn
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            + PREFIX_PROFILEPAGE + "PROFILE PAGE "
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            + PREFIX_PROFILEPAGE + "www.facebook.com "
```
###### \java\seedu\address\logic\commands\DeleteListCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Deletes a list of persons identified using their last displayed indices from the address book.
 */
public class DeleteListCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the list of persons identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDICES (must be a positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 4";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons: %1$s";

    private final List<Index> listTargetIndices;

    public DeleteListCommand(List<Index> listTargetIndices) {
        this.listTargetIndices = listTargetIndices;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex: listTargetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        for (Index targetIndex: listTargetIndices) {
            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            listPersonsToDelete.add(personToDelete);
        }

        try {
            for (ReadOnlyPerson personToDelete: listPersonsToDelete) {
                model.deletePerson(personToDelete);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && (this.listTargetIndices.containsAll(((DeleteListCommand) other).listTargetIndices)
                    && ((DeleteListCommand)other).listTargetIndices
                    .containsAll(this.listTargetIndices))); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            + "[" + PREFIX_PROFILEPAGE + "PROFILE PAGE] "
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        private ProfilePage profile;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setProfilePage(ProfilePage profile) {
            this.profile = profile;
        }

        public Optional<ProfilePage> getProfilePage() {
            return Optional.ofNullable(profile);
        }
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.*;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Created by nguyenminhquang on 11/7/17.
 */

/**
 * Export the contact list into XML file and store it at the specified input path
 */

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the contact list into XML file and store it at the specified input path \n"
            + "Parameters: [FILE_PATH]...\n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\(username)\\Desktop\n" +
            "\n";

    private final String savedFilePath;

    public ExportCommand(String savedFilePath) {
        this.savedFilePath = savedFilePath;
    }

    @Override
    public CommandResult execute() {
        try {
            File file;
            file = new File(savedFilePath);


            File fXmlFile = new File(System.getProperty("user.dir") + "/data/addressbook.xml");
            System.out.println(System.getProperty("user.dir") + "/data/addressbook.xml");
            //PrintWriter output = null;
            //PrintWriter output = new PrintWriter(new FileWriter(file), "UTF-8");
            PrintWriter output = new PrintWriter(savedFilePath, "UTF-8");
            //File fXmlFile = new File("/Users/mkyong/staff.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            output.println("Contact List :");
            NodeList personList = doc.getElementsByTagName("persons");
            output.println("----------------------------");

            for (int temp = 0; temp < personList.getLength(); temp++) {
                Node nNode = personList.item(temp);
                output.println("\nPerson :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    output.println("Name : "
                            + eElement.getElementsByTagName("name")
                            .item(0).getTextContent());
                    //output.println("Name : "
                    //      + eElement.getAttribute("name"));
                    output.println("Phone : "
                            + eElement.getElementsByTagName("phone")
                            .item(0).getTextContent());
                    output.println("Email : "
                            + eElement.getElementsByTagName("email")
                            .item(0).getTextContent());
                    output.println("Birthday : "
                            + eElement.getElementsByTagName("birthday")
                            .item(0).getTextContent());
                    output.println("Address : "
                            + eElement.getElementsByTagName("address")
                            .item(0).getTextContent());
                    output.println("Profile Page : "
                            + eElement.getElementsByTagName("profile")
                            .item(0).getTextContent());
                }

            }
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CommandResult("File exported at " + savedFilePath +" .");
        // export your data code here
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.savedFilePath.equals(((ExportCommand) other).savedFilePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindPhoneCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsPhonePredicate;

/**
 * Finds and lists all persons in address book whose phone numbers match with any of the argument phone numbers.
 */

public class FindPhoneCommand extends Command {
    public static final String COMMAND_WORD = "phone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the persons whose phone numbers "
            + "appear in the list of specified numbers and displays those persons as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 12345678";

    private final NameContainsPhonePredicate predicate;

    public FindPhoneCommand(NameContainsPhonePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindPhoneCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_PROFILEPAGE = new Prefix("pr/");
```
###### \java\seedu\address\logic\parser\DeleteListCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteListCommand object
 */
public class DeleteListCommandParser implements Parser<DeleteListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteListCommand
     * and returns an DeleteListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteListCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteListCommand.MESSAGE_USAGE));
            }
            String[] indices = trimmedArgs.split("\\s+");
            List<String> inputInString = Arrays.asList(indices);

            List<Index> input = new ArrayList<Index>();

            for (String ind: inputInString) {
                Index index = ParserUtil.parseIndex(ind);
                input.add(index);
            }

            return new DeleteListCommand(input);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteListCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    private Optional<ProfilePage> parseProfilePageForEdit(Optional<String> profile) throws IllegalValueException {
        requireNonNull(profile);
        return profile.isPresent() ? Optional.of(new ProfilePage(profile.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Created by nguyenminhquang on 11/7/17.
 */


/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class ExportCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if(args.equals("") || args.isEmpty()){
            return new ExportCommand("exportFile.txt");
        } else {
            return new ExportCommand(args);
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindPhoneCommandParser.java
``` java
package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsPhonePredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns an FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneNumbers = trimmedArgs.split("\\s+");

        return new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(phoneNumbers)));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> profile} into an {@code Optional<ProfilePage>} if {@code profile} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePage> parseProfilePage(Optional<String> profile) throws IllegalValueException {
        requireNonNull(profile);
        return profile.isPresent() ? Optional.of(new ProfilePage(profile.get())) : Optional.of(new ProfilePage(""));
    }
```
###### \java\seedu\address\model\person\NameContainsPhonePredicate.java
``` java
package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the phones given.
 */

public class NameContainsPhonePredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> numbers;

    public NameContainsPhonePredicate(List<String> numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return numbers.stream()
                .anyMatch(number -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), number));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsPhonePredicate // instanceof handles nulls
                && this.numbers.equals(((NameContainsPhonePredicate) other).numbers)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    private ObjectProperty<ProfilePage> profile;
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setProfilePage(ProfilePage profile) { this.profile.set(requireNonNull(profile));}

    @Override
    public ObjectProperty<ProfilePage> profilepageProperty() { return profile; }

    @Override
    public ProfilePage getProfilePage() {return profile.get(); }
```
###### \java\seedu\address\model\person\ProfilePage.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//import org.apache.commons.validator.routines.UrlValidator;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's profile page in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidProfilePage(String)}
 */
public class ProfilePage {

    public static final String MESSAGE_PROFILEPAGE_CONSTRAINTS =
            "Person Profile page should be a valid URL pointing to that person's profile";
    public static final String PROFILEPAGE_VALIDATION_REGEX= "^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]+.[a-z]{3}.([a-z]+)((/)?[a-zA-Z0-9.]?)*?$";
    //public static final String PROFILEPAGE_VALIDATION_REGEX = "^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
    //public static final String PROFILEPAGE_VALIDATION_REGEX = "^(https?:\\/\\/)?(www\\.)?([\\w]+\\.)+[‌​\\w]{2,63}\\/?$";
    //public static final String PROFILEPAGE_VALIDATION_REGEX = "^(http://|https://)?(www.).(facebook.com/){1}[\\w/]{0,63}$";
    //public static final String PROFILEPAGE_VALIDATION_REGEX ="^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
    //public static final String PROFILEPAGE_VALIDATION_REGEX = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";

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
        if(!profileLink.equals("") && !profileLink.endsWith("/")) {
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
        /*
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(test);
        */
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
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<ProfilePage> profilepageProperty();
    ProfilePage getProfilePage();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = false)
    private String profile="";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadProfilePage(ReadOnlyPerson person) {
        loadPage("https://" + person.getProfilePage().toString());
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;
        if(person.getProfilePage().hasProfilePage()) {
            loadProfilePage(person);
        } else {
            loadPersonPage(person);
        }


    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label profile;
```
###### \resources\view\PersonListCard.fxml
``` fxml
      <Label fx:id="profile" styleClass="cell_small_label" text="\$profile" visible="false" />
```
