package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.sortAllPersons;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;
    private ListCommand listCommandNameDefault;
    private ListCommand listCommandNameDescending;
    private ListCommand listCommandNameAscending;
    private ListCommand listCommandPhoneDefault;
    private ListCommand listCommandPhoneDescending;
    private ListCommand listCommandPhoneAscending;
    private ListCommand listCommandEmailDefault;
    private ListCommand listCommandEmailDescending;
    private ListCommand listCommandEmailAscending;
    private ListCommand listCommandAddressDefault;
    private ListCommand listCommandAddressDescending;
    private ListCommand listCommandAddressAscending;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand(new ArrayList<>());
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandNameDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DEFAULT));
        listCommandNameDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DESCENDING));
        listCommandNameDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_ASCENDING));
        listCommandNameAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandPhoneDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DEFAULT));
        listCommandPhoneDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DESCENDING));
        listCommandPhoneDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_ASCENDING));
        listCommandPhoneAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandEmailDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DEFAULT));
        listCommandEmailDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DESCENDING));
        listCommandEmailDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_ASCENDING));
        listCommandEmailAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandAddressDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DEFAULT));
        listCommandAddressDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DESCENDING));
        listCommandAddressDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_ASCENDING));
        listCommandAddressAscending.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByNameDefault_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DEFAULT);
        assertCommandSuccess(listCommandNameDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByNameDescending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DESCENDING);
        assertCommandSuccess(listCommandNameDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByNameAscending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_ASCENDING);
        assertCommandSuccess(listCommandNameAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByPhoneDefault_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DEFAULT);
        assertCommandSuccess(listCommandPhoneDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByPhoneDescending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DESCENDING);
        assertCommandSuccess(listCommandPhoneDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByPhoneAscending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_ASCENDING);
        assertCommandSuccess(listCommandPhoneAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByEmailDefault_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DEFAULT);
        assertCommandSuccess(listCommandEmailDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByEmailDescending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DESCENDING);
        assertCommandSuccess(listCommandEmailDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByEmailAscending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_ASCENDING);
        assertCommandSuccess(listCommandEmailAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByAddressDefault_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DEFAULT);
        assertCommandSuccess(listCommandAddressDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByAddressDescending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DESCENDING);
        assertCommandSuccess(listCommandAddressDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSortedByAddressAscending_showsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_ASCENDING);
        assertCommandSuccess(listCommandAddressAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
