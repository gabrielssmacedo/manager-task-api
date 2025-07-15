package gsm.task.manager.domain.enums;

public enum Category {
    STUDYING("studying"),
    WORKOUT("workout"),
    WORKING("working"),
    READING("reading"),
    DRINK_WATER("drink water"),
    CLEANING("cleaning"),
    PLANNING("planning"),
    OTHERS("others");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
