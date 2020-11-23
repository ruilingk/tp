package seedu.tr4cker.ui.planner;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import seedu.tr4cker.commons.core.LogsCenter;
import seedu.tr4cker.model.planner.PlannerDay;
import seedu.tr4cker.model.task.Task;
import seedu.tr4cker.model.task.TaskDueInPredicate;
import seedu.tr4cker.ui.MainWindow;
import seedu.tr4cker.ui.UiPart;

/**
 * An UI component that displays information of PlannerDay.
 * Displays details of a day in the calendar.
 *
 * Solution below adapted from
 * https://github.com/AY1920S2-CS2103T-F09-2/main/blob/master/src/main/java/seedu/jelphabot/ui/CalendarDayCard.java
 */
public class PlannerDayCard extends UiPart<Region> {

    private static final String FXML = "PlannerDayCard.fxml";
    private final Logger logger = LogsCenter.getLogger(PlannerDayCard.class);

    private final PlannerDay plannerDay;

    @FXML
    private Circle circle;

    @FXML
    private Label dateNumber;

    @FXML
    private Rectangle coverUp;

    @FXML
    private Rectangle indicator;

    /**
     * Creates a {@code PlannerDayCard} with the given {@code PlannerDay} to display.
     */
    public PlannerDayCard(PlannerDay plannerDay) {
        super(FXML);
        logger.fine("Initialising plannerDayCard...");
        requireNonNull(plannerDay);

        this.plannerDay = plannerDay;
        dateNumber.setText(Integer.toString(plannerDay.getDay()));
        circle.setId("circle-not-today");
        setIndicator();
    }

    /**
     * Clears the date number and highlight of a PlannerDayCard.
     */
    public void clear() {
        logger.fine("Clearing dates...");
        dateNumber.setText("");
        circle.setId("circle-not-today");
        logger.fine("Cleared dates.");
    }

    /**
     * Sets the date of the same month to be black colour.
     */
    public void setSameMonthColour() {
        dateNumber.setId("same-month-colour");
    }

    /**
     * Sets the date of the same month to be grey colour.
     */
    public void setDifferentMonthColour() {
        dateNumber.setId("different-month-colour");
    }

    /**
     * Highlights the date.
     */
    public void setToday() {
        circle.setId("circle-today");
    }

    /**
     * Sets the indicator of a Planner Day Card depending on number of tasks due on that day.
     */
    private void setIndicator() {
        logger.fine("Setting indicators...");
        setNoIndicator();
        ObservableList<Task> filteredList = MainWindow.getLogic().getFilteredTaskList();
        LocalDate localDate = plannerDay.getLocalDate();
        int numOfTasks = filteredList.filtered(new TaskDueInPredicate(localDate)).size();
        if (numOfTasks == 0) {
            setNoIndicator();
        } else if (numOfTasks <= 2) {
            setGreenIndicator();
        } else {
            setRedIndicator();
        }
        logger.fine("Set indicators.");
    }

    /**
     * Sets indicator to no colour (0 tasks).
     */
    private void setNoIndicator() {
        coverUp.setId("indicator-no-tasks");
        indicator.setId("invisible");
    }

    /**
     * Sets indicator to green colour (1 or 2 tasks).
     */
    private void setGreenIndicator() {
        coverUp.setId("invisible");
        indicator.setId("indicator-green");
    }

    /**
     * Sets indicator to red colour (more than 2 tasks).
     */
    private void setRedIndicator() {
        coverUp.setId("invisible");
        indicator.setId("indicator-red");
    }

    /**
     * Updates indicator of Planner Day Card.
     */
    public void updateIndicator() {
        setIndicator();
    }

}
