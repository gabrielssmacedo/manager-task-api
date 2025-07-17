package gsm.task.manager.domain.service;

import gsm.task.manager.domain.dto.TaskRequestDTO;
import gsm.task.manager.domain.enums.StatusTask;
import gsm.task.manager.domain.exceptions.TaskNotFoundException;
import gsm.task.manager.domain.model.Task;
import gsm.task.manager.domain.service.utils.ValidationTask;
import gsm.task.manager.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> tasksFound = taskRepository.findAll();

        tasksFound = tasksFound.stream()
                .map(task -> {
                    ValidationTask.convertToLocalDate(task);
                    return ValidationTask.validateStatus(task);})
                .toList();

        if(tasksFound.isEmpty()) throw new TaskNotFoundException("there are not tasks created");
        return tasksFound;
    }

    public Task findTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("task not found"));
        ValidationTask.convertToLocalDate(task);
        return ValidationTask.validateStatus(task);
    }

    public List<Task> findWeeklyTasks() {
        List<Task> allTasks = taskRepository.findAll();

        allTasks = allTasks.stream().map(task -> {
            ValidationTask.convertToLocalDate(task);
            return ValidationTask.validateStatus(task);
        }).toList();

        LocalDateTime now = LocalDateTime.now();

        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(now.plusDays(7L).withHour(23).withMinute(59))
                        && task.getDatetimeLimit().isAfter(now.minusDays(1L).withHour(23).withMinute(59))
                        && task.getStatus() != StatusTask.DONE)
                .toList();
    }

    public List<Task> findTaskForToday() {
        List<Task> allTasks = taskRepository.findAll();

        allTasks = allTasks.stream().map(task -> {
            ValidationTask.convertToLocalDate(task);
            return ValidationTask.validateStatus(task);
        }).toList();

        LocalDateTime now = LocalDateTime.now();

        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(now.withHour(23).withMinute(59))
                        && task.getDatetimeLimit().isAfter(now.minusDays(1L).withHour(23).withMinute(59))
                        && task.getStatus() != StatusTask.DONE)
                .toList();
    }

    public List<Task> findTasksByStatus(StatusTask statusTask) {
        List<Task> allTasks = taskRepository.findAll();

        allTasks = allTasks.stream()
                .map(task -> {
                    ValidationTask.convertToLocalDate(task);
                    return ValidationTask.validateStatus(task);})
                .toList();

        return allTasks.stream()
                .filter(task -> task.getStatus() == statusTask)
                .toList();
    }

    public Task createTask(TaskRequestDTO taskDTO) {
        Task task = new Task(taskDTO);
        ValidationTask.validateStatus(task);
        ValidationTask.convertToGlobalDate(task);
        Task taskSaved = taskRepository.save(task);
        ValidationTask.convertToLocalDate(taskSaved);
        return taskSaved;
    }

    public void deleteTaskById(Long id) {
        if(taskRepository.existsById(id)) taskRepository.deleteById(id);
        else throw new RuntimeException("task not found");
    }

    public Task updateTask(Long id, TaskRequestDTO taskDTO) {
        Task taskToUpdate = new Task(taskDTO);
        Task taskUpdated = findTaskById(id);

        ValidationTask.updateData(taskUpdated, taskToUpdate);
        ValidationTask.validateStatus(taskUpdated);
        ValidationTask.convertToGlobalDate(taskUpdated);
        taskUpdated = taskRepository.save(ValidationTask.validateStatus(taskUpdated));
        ValidationTask.convertToLocalDate(taskUpdated);

        return taskUpdated;
    }

    public Task closeTask(Long id) {
        Task taskToClose = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("task not found"));
        taskToClose.setStatus(StatusTask.DONE);
        return taskRepository.save(taskToClose);
    }


}
