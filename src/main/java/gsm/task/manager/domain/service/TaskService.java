package gsm.task.manager.domain.service;

import gsm.task.manager.domain.model.Task;
import gsm.task.manager.repository.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Tag(name = "Tasks")
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> tasksFound = taskRepository.findAll();
        if(tasksFound.isEmpty()) throw new RuntimeException("Task not found");
        return tasksFound;
    }

    public Task createTask(Task task) {
        if(task == null) throw new RuntimeException();
        return taskRepository.save(task);
    }
}
