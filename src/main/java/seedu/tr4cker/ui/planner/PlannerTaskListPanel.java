package seedu.tr4cker.ui.planner;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tr4cker.commons.core.LogsCenter;
import seedu.tr4cker.model.task.Task;
import seedu.tr4cker.ui.TaskCard;
import seedu.tr4cker.ui.UiPart;

/**
 * Panel containing the list of tasks in Planner.
 *
 * Solution below adapted from
 * https://github.com/AY1920S2-CS2103T-F09-2/main/blob/master/src/main/java/seedu/jelphabot
 * /ui/CalendarTaskListPanel.java
 */
public class PlannerTaskListPanel extends UiPart<Region> {

    private static final String FXML = "PlannerTaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlannerTaskListPanel.class);

    @FXML
    private Label plannerTaskListPanelTitle;

    @FXML
    private ListView<Task> taskListView;

    /**
     * Creates a {@code PlannerTaskListPanel} with the given {@code ObservableList}.
     */
    public PlannerTaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        logger.fine("Initialising Planner Task List panel...");
        requireNonNull(taskList);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        logger.fine("Created Planner Task List panel.");
    }

    /**
     * Updates the title of the task list panel.
     *
     * @param title New title.
     */
    public void updateTitle(String title) {
        requireNonNull(title);
        logger.fine("Updating date of planner task list panel to: " + title);
        plannerTaskListPanelTitle.setText("Tasks due on: " + title);
        logger.fine("Updated date of planner task list panel to: " + title);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    static class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
