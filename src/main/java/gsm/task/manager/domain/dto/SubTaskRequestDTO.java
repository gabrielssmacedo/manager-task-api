package gsm.task.manager.domain.dto;

import gsm.task.manager.domain.enums.Priority;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record SubTaskRequestDTO(@NotNull @Length(max = 200) String description, Priority priority) {
}
