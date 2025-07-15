package gsm.task.manager.domain.service;

import gsm.task.manager.domain.model.Task;
import gsm.task.manager.repository.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Task> findWeeklyTasks() {
        List<Task> allTasks = findAllTasks();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now().plusDays(8L)))
                .toList();
    }

    public List<Task> findTaskForToday() {
        List<Task> allTasks = findAllTasks();
        LocalDateTime now = LocalDateTime.now();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now().plusHours(24 - now.getHour())))
                .toList();
    }

    public Task createTask(Task task) {
        if(task == null) throw new RuntimeException();
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        if(taskRepository.existsById(id)) taskRepository.deleteById(id);
        else throw new RuntimeException("Task not found");
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
