package gsm.task.manager.domain.service;

import gsm.task.manager.domain.dto.SubTaskRequestDTO;
import gsm.task.manager.domain.exceptions.SubTaskException;
import gsm.task.manager.domain.exceptions.TaskNotFoundException;
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
        if(!taskRepository.existsById(taskId)) throw new TaskNotFoundException("task not found");
        subtasks =  subtasks.stream()
                .filter(sub -> Objects.equals(sub.getTask().getId(), taskId))
                .toList();
        if(subtasks.isEmpty()) throw new SubTaskException("subtask not found for this task");
        return subtasks;
    }

    public SubTask createSubtask(SubTaskRequestDTO subTaskDTO, Long idTask) {
        Task task = taskRepository.findById(idTask).orElseThrow(()-> new TaskNotFoundException("task not found"));
        SubTask subTask = new SubTask(subTaskDTO);
        subTask.setTask(task);
        return subTaskRespository.save(subTask);
    }

    public void deleteSubtask(Long id) {
        if(subTaskRespository.existsById(id))
            subTaskRespository.deleteById(id);
        else throw new SubTaskException("subtask not found");
    }

}
