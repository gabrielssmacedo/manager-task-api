package gsm.task.manager.domain.dto;

import gsm.task.manager.domain.enums.Category;
import gsm.task.manager.domain.enums.Priority;
import java.time.LocalDateTime;

public record TaskRequestDTO(String title, String shortDescription, Category category,
                             Priority priority, LocalDateTime datetimeLimit) {
}
