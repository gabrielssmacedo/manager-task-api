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

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Task createTask(Task task) {
        if(task == null) throw new RuntimeException();
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = findTaskById(id);
        updateData(taskToUpdate, task);
        return taskRepository.save(taskToUpdate);
    }

    private void updateData(Task taskToUpdate, Task taskUpdated) {
        //update title
        if(!taskUpdated.getTitle().isEmpty())
            taskToUpdate.setTitle(taskUpdated.getTitle());

        //update description
        if(!taskUpdated.getShortDescription().isEmpty()) {
            taskUpdated.setShortDescription(taskUpdated.getShortDescription());
        }

        //update priority
        if(taskUpdated.getPriority().getDescription() != null) {
            taskToUpdate.setPriority(taskUpdated.getPriority());
        }

        //udpdate category
        if(taskUpdated.getCategory().getDescription() != null) {
            taskToUpdate.setPriority(taskUpdated.getPriority());
        }

        //update datetime
        taskToUpdate.setDatetimeLimit(taskUpdated.getDatetimeLimit());
    }

}
