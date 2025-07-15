package gsm.task.manager.domain.enums;

public enum Priority {
    LOW("low"),
    MIDDLE("middle"),
    HIGH("high");

    private String description;

    Priority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
