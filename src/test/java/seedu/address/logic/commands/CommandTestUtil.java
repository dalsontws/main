package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.QuestionContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_QUESTION_1 = "When is WWII?";
    public static final String VALID_QUESTION_2 = "Where is NUS?";
    public static final String VALID_ANSWER_1 = "1 September 1939 – 2 September 1945";
    public static final String VALID_ANSWER_2 = "Kent Ridge";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_RATING_1 = "good";
    public static final String VALID_RATING_2 = "easy";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String QUESTION_DESC_1 = " " + PREFIX_QUESTION + VALID_QUESTION_1;
    public static final String QUESTION_DESC_2 = " " + PREFIX_QUESTION + VALID_QUESTION_2;
    public static final String ANSWER_DESC_1 = " " + PREFIX_ANSWER + VALID_ANSWER_1;
    public static final String ANSWER_DESC_2 = " " + PREFIX_ANSWER + VALID_ANSWER_2;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String RATING_DESC_1 = " " + PREFIX_RATING + VALID_RATING_1;
    public static final String RATING_DESC_2 = " " + PREFIX_RATING + VALID_RATING_2;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + " "; // ' ' not allowed in questions
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + " "; // ' ' not allowed in answers
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING; // empty string not allowed for rating
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_1;
    public static final EditCommand.EditPersonDescriptor DESC_2;

    static {
        DESC_1 = new EditPersonDescriptorBuilder().withQuestion(VALID_QUESTION_1)
                .withAnswer(VALID_ANSWER_1).withEmail(VALID_EMAIL_AMY).withRating(VALID_RATING_1)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_2 = new EditPersonDescriptorBuilder().withQuestion(VALID_QUESTION_2)
                .withAnswer(VALID_ANSWER_2).withEmail(VALID_EMAIL_BOB).withRating(VALID_RATING_2)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashCardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredFlashCardList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashCardList().size());

        Person person = model.getFilteredFlashCardList().get(targetIndex.getZeroBased());
        final String[] question = person.getQuestion().fullQuestion.split("\\s+");
        model.updateFilteredPersonList(new QuestionContainsAllKeywordsPredicate(Arrays.asList(question)));

        assertEquals(1, model.getFilteredFlashCardList().size());
    }

}
