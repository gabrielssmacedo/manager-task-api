package gsm.task.manager.domain.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("low"),
    MIDDLE("middle"),
    HIGH("high");

    private final String description;

    Priority(String description) {
        this.description = description;
    }
}
