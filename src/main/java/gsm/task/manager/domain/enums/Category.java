package gsm.task.manager.domain.enums;

import lombok.Getter;

@Getter
public enum Category {
    STUDYING("studying"),
    WORKOUT("workout"),
    WORKING("working"),
    READING("reading"),
    DRINK_WATER("drink water"),
    CLEANING("cleaning"),
    PLANNING("planning"),
    OTHERS("others");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}
