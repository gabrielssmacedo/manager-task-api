package gsm.task.manager.domain.service;

import gsm.task.manager.domain.enums.StatusTask;
import gsm.task.manager.domain.model.Task;
import gsm.task.manager.repository.TaskRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> tasksFound = taskRepository.findAll();
        tasksFound = tasksFound.stream().map(task -> {
            convertDateToLocal(task);
            return validateStatus(task);
        }).toList();

        if(tasksFound.isEmpty()) throw new RuntimeException("Task not found");
        return tasksFound;
    }

    public Task findTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(RuntimeException::new);
        convertDateToLocal(task);
        return validateStatus(task);
    }

    public List<Task> findWeeklyTasks() {
        List<Task> allTasks = findAllTasks();
        allTasks = allTasks.stream().map(task -> {
            convertDateToLocal(task);
            return validateStatus(task);
        }).toList();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now().plusDays(8L)))
                .toList();
    }

    public List<Task> findTaskForToday() {
        List<Task> allTasks = findAllTasks();
        allTasks = allTasks.stream().map(task -> {
            convertDateToLocal(task);
            return validateStatus(task);
        }).toList();
        LocalDateTime now = LocalDateTime.now();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(now.plusHours(24 - now.getHour())))
                .toList();
    }

    public List<Task> findTasksLated() {
        List<Task> allTasks = findAllTasks();
        allTasks = allTasks.stream().map(task -> {
            convertDateToLocal(task);
            return validateStatus(task);
        }).toList();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now()))
                .toList();
    }

    public Task createTask(Task task) {
        if(task == null) throw new RuntimeException();
        if(task.getStatus() != null) throw new RuntimeException("Isn't possible define a Status on creation");
        validateStatus(task);
        Task taskSaved = taskRepository.save(task);
        convertDateToLocal(taskSaved);
        return taskSaved;
    }

    public void deleteTaskById(Long id) {
        if(taskRepository.existsById(id)) taskRepository.deleteById(id);
        else throw new RuntimeException("Task not found");
    }

    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = findTaskById(id);
        updateData(taskToUpdate, task);
        validateStatus(task);
        taskToUpdate = taskRepository.save(validateStatus(taskToUpdate));
        convertDateToLocal(taskToUpdate);
        return taskToUpdate;
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

    private Task validateStatus(Task task) {
        if(task.getDatetimeLimit().isAfter(LocalDateTime.now()) && task.getStatus() != StatusTask.DONE)
            task.setStatus(StatusTask.TO_DO);
        else task.setStatus(StatusTask.LATE);
        return task;
    }

    private void convertDateToLocal(Task task) {
        Instant inst = Instant.from(task.getDatetimeLimit());
        task.setDatetimeLimit(LocalDateTime.ofInstant(inst, ZoneId.systemDefault()));
    }
}
