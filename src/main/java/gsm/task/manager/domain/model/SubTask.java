package gsm.task.manager.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gsm.task.manager.domain.dto.SubTaskRequestDTO;
import gsm.task.manager.domain.enums.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_subtask")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTask {

    public SubTask(SubTaskRequestDTO subTaskDTO) {
        this.description = subTaskDTO.description();
        this.priority = subTaskDTO.priority();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtask_description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
