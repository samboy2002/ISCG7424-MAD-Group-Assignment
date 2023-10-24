package unitec.iscg7424.groupassignment.models;

import java.util.ArrayList;
import java.util.List;

public class AcceptedTask {
    private String taskId;
    private List<TaskHistory> histories = new ArrayList<>();

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<TaskHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<TaskHistory> histories) {
        this.histories = histories;
    }
}
