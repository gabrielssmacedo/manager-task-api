package gsm.task.manager.domain.enums;

import lombok.Getter;

@Getter
public enum StatusTask {
    LATE("late"),
    TO_DO("to do"),
    DONE("done");

    private final String description;

    StatusTask(String description) {
        this.description = description;
    }
}
