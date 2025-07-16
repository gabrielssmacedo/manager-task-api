package gsm.task.manager.domain.dto;

import gsm.task.manager.domain.enums.Priority;

public record SubTaskRequestDTO(String description, Priority priority) {
}
