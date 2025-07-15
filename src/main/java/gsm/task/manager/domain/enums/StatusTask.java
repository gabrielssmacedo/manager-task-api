package gsm.task.manager.domain.enums;

public enum StatusTask {
    LATE("late"),
    TO_DO("to do"),
    IN_PROGRESS("in progress"),
    DONE("done");

    private String description;

    StatusTask(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
