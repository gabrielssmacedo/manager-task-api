package gsm.task.manager.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.task.manager.domain.enums.Category;
import gsm.task.manager.domain.enums.Priority;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TaskRequestDTO(@NotNull @Length(max = 50) String title, @Length(max = 200) String shortDescription,
                             Category category, @NotNull Priority priority,
                             @NotNull @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime datetimeLimit) {

}
