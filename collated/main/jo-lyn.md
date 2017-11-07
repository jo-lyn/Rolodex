# jo-lyn
###### \java\seedu\address\commons\events\ui\ClearPersonDetailPanelRequestEvent.java
``` java
/**
 * Indicates a request to clear the person detail panel.
 */
public class ClearPersonDetailPanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PersonEditedEvent.java
``` java
/**
 * Indicates that a person has been edited.
 */
public class PersonEditedEvent extends BaseEvent {

    public final ReadOnlyPerson editedPerson;
    public final int targetIndex;

    public PersonEditedEvent(ReadOnlyPerson person, Index index) {
        editedPerson = person;
        targetIndex = index.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Adds a remark to an existing person in the rolodex.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "note", "rmk", "comment"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the rolodex.";

    private final Index index;
    private final Remark remark;

    /**
     * Changes the remark of an existing person in the rolodex.
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getLatestPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @return the success message for the remark command
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles null
        if (!(other instanceof RemarkCommand)) {
            return false;
        }
        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (ReadOnlyPerson person: rolodex.getPersonList()) {
            Person newPerson = new Person(person);

            Set<Tag> newTags = new HashSet<>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);

            rolodex.updatePerson(person, newPerson);
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns the index of the given person
     */
    public Index getIndex(ReadOnlyPerson target) {
        return Index.fromZeroBased(sortedPersons.indexOf(target));
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the rolodex.
 * Guarantees: immutable, is always valid.
 */
public class Remark {
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles null
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(Remark other) {
        return toString().compareTo(other.toString());
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public void setFocus() {
        commandTextField.requestFocus();
    }

    public boolean isFocused() {
        return commandTextField.isFocused();
    }

    /**
     * Load images for keyboard icons in the command box.
     */
    private void loadKeyboardIcons() {
        keyboardIdle = new Image(getClass().getResourceAsStream("/images/keyboard.png"));
        keyboardTyping = new Image(getClass().getResourceAsStream("/images/keyboardTyping.png"));
        keyboardError = new Image(getClass().getResourceAsStream("/images/keyboardError.png"));
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Change {@code keyboardTyping} icon to {@code keyboardIdle} when there is no change
     * to text field after some time.
     */
    private void updateKeyboardIcon() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        keyboardIcon.setImage(keyboardTyping);
        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    private void setErrorKeyboardIcon() {
        keyboardIcon.setImage(keyboardError);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Set key listeners for handling keyboard shortcuts.
     */
    protected void setKeyListeners() {
        KeyListener keyListener = new KeyListener(getRoot(), resultDisplay, personListPanel, commandBox);
        keyListener.handleKeyPress();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        personListPanel = new PersonListPanel(logic.getLatestPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Proportions the split pane divider position according to window size
     */
    private void setSplitPaneDividerProperty() {

        primaryStage.showingProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(0.35f));

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) ->
                splitPane.setDividerPositions(0.35f));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise the person card with the person details.
     */
    private void initialisePerson(ReadOnlyPerson person, int displayedIndex) {
        id.setText(Integer.toString(displayedIndex));
        setAvatar(person);
        setTags(person);
    }

    private void setAvatar(ReadOnlyPerson person) {
        initial.setText(Avatar.getInitial(person.getName().fullName));
        avatar.setFill(Paint.valueOf(Avatar.getColor(person.getName().fullName)));
    }

    private void setTags(ReadOnlyPerson person) {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
```
###### \java\seedu\address\ui\util\ColorsUtil.java
``` java
/**
 * A utility class for colors used in UI.
 */
public class ColorsUtil {
    public static final String RED = "#d06651";
    public static final String YELLOW = "#f1c40f";
    public static final String BLUE = "#3498db";
    public static final String TEAL = "#1abc9c";
    public static final String GREEN = "#2ecc71";
    public static final String PURPLE = "#9b59b6";

    private ColorsUtil() {} // prevents instantiation

    public static String[] getColors() {
        return new String[] { RED, YELLOW, BLUE, TEAL, GREEN, PURPLE };
    }
}
```
