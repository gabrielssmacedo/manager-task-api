package gsm.task.manager.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import gsm.task.manager.domain.dto.TaskRequestDTO;
import gsm.task.manager.domain.enums.Category;
import gsm.task.manager.domain.enums.Priority;
import gsm.task.manager.domain.enums.StatusTask;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_task")
public class Task {

    public Task(TaskRequestDTO taskDTO) {
        this.title = taskDTO.title();
        this.shortDescription = taskDTO.shortDescription();
        this.category = taskDTO.category();
        this.priority = taskDTO.priority();
        this.datetimeLimit = taskDTO.datetimeLimit();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String shortDescription;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "status_task")
    @Enumerated(EnumType.STRING)
    private StatusTask status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "datetime_task")
    private LocalDateTime datetimeLimit;

    private String icon;

    @OneToMany(mappedBy = "task")
    private List<SubTask> subTasks;
}
