package gsm.task.manager.domain.service;

import gsm.task.manager.domain.model.SubTask;
import gsm.task.manager.domain.model.Task;
import gsm.task.manager.repository.SubTaskRespository;
import gsm.task.manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubTaskService {

    private final SubTaskRespository subTaskRespository;
    private final TaskRepository taskRepository;

    public SubTaskService(SubTaskRespository subTaskRespository, TaskRepository taskRepository) {
        this.subTaskRespository = subTaskRespository;
        this.taskRepository = taskRepository;
    }

    public List<SubTask> findAllSubtasksOfTask(Long taskId) {
        List<SubTask> subtasks = subTaskRespository.findAll();
        if(!taskRepository.existsById(taskId)) throw new RuntimeException("Task not found");
        subtasks =  subtasks.stream()
                .filter(sub -> Objects.equals(sub.getTask().getId(), taskId))
                .toList();
        if(subtasks.isEmpty()) throw new RuntimeException("Subtask not found for this task");
        return subtasks;
    }

    public SubTask createSubtask(SubTask subTask) {
        if(subTask == null) throw new RuntimeException("Subtask not instancead");
        return subTaskRespository.save(subTask);
    }

    public void deleteSubtask(Long id) {
        if(subTaskRespository.existsById(id))
            subTaskRespository.deleteById(id);
        else throw new RuntimeException("SubTask not found");
    }

}
