package seedu.tr4cker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tr4cker.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tr4cker.logic.parser.CliSyntax.PREFIX_PLANNER_GOTO;

import java.time.LocalDate;
import java.time.YearMonth;

import seedu.tr4cker.model.Model;
import seedu.tr4cker.model.task.TaskDueInPredicate;
import seedu.tr4cker.model.util.GotoDateUtil;

/**
 * Allows user to go to Planner tab or go to specific date/month of calendar in Planner tab.
 *
 * Solution below adapted from
 * https://github.com/AY1920S2-CS2103T-F09-2/main/blob/master/src/main/java/seedu/jelphabot
 * /logic/commands/CalendarCommand.java
 */
public class PlannerCommand extends Command {

    public static final String COMMAND_WORD = "planner";

    public static final String MESSAGE_SWITCH_TAB_USAGE = COMMAND_WORD + ": Switches to Planner tab\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_GOTO_USAGE = COMMAND_WORD + ": Goes to a specific date/month on Planner tab\n"
            + "Parameters: "
            + PREFIX_PLANNER_GOTO + "DAY\n"
            + "Examples: \n"
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "today OR "
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "tdy\n"
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "tomorrow OR "
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "tmr\n"
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "12-2020 OR "
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "Dec-2020\n"
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "25-12-2020 OR "
            + COMMAND_WORD + " " + PREFIX_PLANNER_GOTO + "25-Dec-2020";

    public static final String MESSAGE_SWITCH_TAB_SUCCESS = "Switched to Planner tab!";

    public static final String MESSAGE_GOTO_DATE_SUCCESS = "Showed tasks on: %1$s";

    private final String message;
    private final LocalDate localDate;
    private final YearMonth yearMonth;
    private final TaskDueInPredicate taskDueInPredicate;

    /**
     * Constructor for PlannerCommand when user wants to switch to Planner tab.
     */
    public PlannerCommand() {
        this.message = null;
        this.localDate = null;
        this.yearMonth = null;
        this.taskDueInPredicate = new TaskDueInPredicate(GotoDateUtil.getToday());
        assert taskDueInPredicate != null : "Predicate should not be null in here.";
    }

    /**
     * Constructor for PlannerCommand when user wants to switch to specific date/month.
     */
    public PlannerCommand(String message, LocalDate localDate, YearMonth yearMonth,
                          TaskDueInPredicate taskDueInPredicate) {
        requireAllNonNull(message, taskDueInPredicate);
        this.message = message;
        this.localDate = localDate;
        this.yearMonth = yearMonth;
        this.taskDueInPredicate = taskDueInPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (message == null && localDate == null && yearMonth == null) {
            model.updatePlannerFilteredTaskList(taskDueInPredicate);
            return new CommandResult(MESSAGE_SWITCH_TAB_SUCCESS, null, null);
        }
        model.updatePlannerFilteredTaskList(taskDueInPredicate);
        String result = String.format(MESSAGE_GOTO_DATE_SUCCESS, message);
        return new CommandResult(result, localDate, yearMonth);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { // short circuit if same object
            return true;
        } else if (!(other instanceof PlannerCommand)) {
            return false;
        } else {
            PlannerCommand plannerCommand = (PlannerCommand) other;
            if (message == null && ((PlannerCommand) other).message == null
                    && taskDueInPredicate.equals(plannerCommand.taskDueInPredicate)
                    && localDate == null && (plannerCommand.localDate == null
                    && yearMonth == null && (plannerCommand.yearMonth == null))) {
                return true;
            } else {
                assert message != null;
                return message.equals(plannerCommand.message) // instanceof handles not null
                        && taskDueInPredicate.equals(plannerCommand.taskDueInPredicate)
                        || localDate == null && plannerCommand.localDate == null
                        || yearMonth == null && plannerCommand.yearMonth == null;
            }
        }
    }

}
