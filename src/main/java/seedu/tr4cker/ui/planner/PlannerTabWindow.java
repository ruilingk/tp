package seedu.tr4cker.ui.planner;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.tr4cker.commons.core.LogsCenter;
import seedu.tr4cker.logic.Logic;
import seedu.tr4cker.logic.commands.CommandResult;
import seedu.tr4cker.model.planner.PlannerDay;
import seedu.tr4cker.model.task.TaskDueInPredicate;
import seedu.tr4cker.model.util.GotoDateUtil;
import seedu.tr4cker.ui.UiPart;

/**
 * An UI component that displays Planner tab.
 * Displays the calendar and tasks list.
 *
 * Solution below adapted from
 * https://github.com/AY1920S2-CS2103T-F09-2/main/blob/master/src/main/java/seedu/jelphabot/ui/CalendarMainPanel.java
 */
public class PlannerTabWindow extends UiPart<Region> {

    private static final String FXML = "PlannerTabWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(PlannerCalendarPanel.class);
    private final PlannerCalendarPanel plannerCalendarPanel;
    private final PlannerTaskListPanel plannerTaskListPanel;

    @FXML
    private StackPane plannerCalendarPanelPlaceholder;
    @FXML
    private StackPane plannerTaskListPanelPlaceholder;

    /**
     * Constructor for PlannerTabWindow.
     *
     * @param logic Logic of TR4CKER.
     */
    public PlannerTabWindow(Logic logic) {
        super(FXML);
        logger.fine("Initialising Planner Tab Window...");
        requireNonNull(logic);

        this.plannerCalendarPanel = new PlannerCalendarPanel(PlannerDay.getCurrDay());
        this.plannerTaskListPanel = new PlannerTaskListPanel(logic.getPlannerFilteredTaskList());
        logic.updatePlannerFilteredTaskList(new TaskDueInPredicate());
        updateToLocalDate(GotoDateUtil.getToday());

        plannerCalendarPanelPlaceholder.getChildren().add(plannerCalendarPanel.getRoot());
        plannerTaskListPanelPlaceholder.getChildren().add(plannerTaskListPanel.getRoot());
        logger.fine("Created Planner Tab Window.");
    }

    /**
     * Updates calendar aspect of Planner tab.
     *
     * @param commandResult CommandResult being passed in.
     */
    public void updateCalendar(CommandResult commandResult) { // from mainwindow
        requireNonNull(commandResult);
        logger.fine("Updating calendar with command result: " + commandResult);
        LocalDate localDate = commandResult.getLocalDate();
        YearMonth yearMonth = commandResult.getYearMonth();

        if (localDate == null && yearMonth == null) {
            updateToLocalDate(GotoDateUtil.getToday());
        } else if (localDate != null && yearMonth == null) { // user wants to go to specified date
            updateToLocalDate(localDate);
        } else if (localDate == null) { // user wants to go to specified month
            updateToYearMonth(yearMonth);
        }
        logger.fine("Updated calendar with command result: " + commandResult);
    }

    /**
     * Updates calendar view to reflect user's input (specific date).
     *
     * @param localDate User's input.
     */
    private void updateToLocalDate(LocalDate localDate) {
        requireNonNull(localDate);
        logger.fine("Updating calendar view to: " + GotoDateUtil.parseGotoDay(localDate));
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int date = localDate.getDayOfMonth();

        plannerCalendarPanel.setCurrentYear(year);
        plannerCalendarPanel.setCurrentMonth(month);

        LocalDate newDate = LocalDate.of(year, month, 1);
        LocalDate userInput = LocalDate.of(year, month, date);
        YearMonth newYearMonth = YearMonth.of(year, month);
        PlannerDay newDay = new PlannerDay(newDate);

        plannerCalendarPanel.clearCalendar();
        plannerCalendarPanel.changeCalendarMonthYear(newYearMonth);
        plannerCalendarPanel.fillCalendarTable(newDay, userInput);
        plannerTaskListPanel.updateTitle(GotoDateUtil.parseGotoDay(localDate));
        logger.fine("Updated calendar view to " + GotoDateUtil.parseGotoDay(localDate));
    }

    /**
     * Updates calendar view to reflect user's input (specific month).
     *
     * @param yearMonth User's input.
     */
    private void updateToYearMonth(YearMonth yearMonth) {
        requireNonNull(yearMonth);
        logger.fine("Updating calendar view to " + GotoDateUtil.parseGotoMonth(yearMonth));
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        plannerCalendarPanel.setCurrentYear(year);
        plannerCalendarPanel.setCurrentMonth(month);

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        PlannerDay newDay = new PlannerDay(firstDayOfMonth);

        plannerCalendarPanel.clearCalendar();
        plannerCalendarPanel.changeCalendarMonthYear(yearMonth);
        plannerCalendarPanel.fillCalendarTable(newDay, null);
        plannerTaskListPanel.updateTitle("01-" + GotoDateUtil.parseGotoMonth(yearMonth));
        logger.fine("Updated calendar view to " + GotoDateUtil.parseGotoMonth(yearMonth));
    }

    /**
     * Updates indicator in calendar panel.
     */
    public void updateIndicator() {
        plannerCalendarPanel.updateIndicator();
    }

}
