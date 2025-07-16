package gsm.task.manager.domain.service;

import gsm.task.manager.domain.dto.TaskRequestDTO;
import gsm.task.manager.domain.enums.StatusTask;
import gsm.task.manager.domain.exceptions.TaskNotFoundException;
import gsm.task.manager.domain.model.Task;
import gsm.task.manager.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            convertToLocalDate(task);
            return validateStatus(task);
        }).toList();

        if(tasksFound.isEmpty()) throw new TaskNotFoundException("there are not tasks created");
        return tasksFound;
    }

    public Task findTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("task not found"));
        convertToLocalDate(task);
        return validateStatus(task);
    }

    public List<Task> findWeeklyTasks() {
        List<Task> allTasks = findAllTasks();
        allTasks = allTasks.stream().map(task -> {
            convertToLocalDate(task);
            return validateStatus(task);
        }).toList();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now().plusDays(8L)))
                .toList();
    }

    public List<Task> findTaskForToday() {
        List<Task> allTasks = findAllTasks();
        allTasks = allTasks.stream().map(task -> {
            convertToLocalDate(task);
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
            convertToLocalDate(task);
            return validateStatus(task);
        }).toList();
        return allTasks.stream()
                .filter(task -> task.getDatetimeLimit().isBefore(LocalDateTime.now()))
                .toList();
    }

    public Task createTask(TaskRequestDTO taskDTO) {
        if(taskDTO == null) throw new RuntimeException();
        Task task = new Task(taskDTO);
        validateStatus(task);
        convertToGlobalDate(task);
        Task taskSaved = taskRepository.save(task);
        convertToLocalDate(taskSaved);
        return taskSaved;
    }

    public void deleteTaskById(Long id) {
        if(taskRepository.existsById(id)) taskRepository.deleteById(id);
        else throw new RuntimeException("task not found");
    }

    public Task updateTask(Long id, TaskRequestDTO taskDTO) {
        if(taskDTO == null) throw new RuntimeException();
        Task taskToUpdate = new Task(taskDTO);
        Task taskUpdated = findTaskById(id);
        updateData(taskUpdated, taskToUpdate);
        validateStatus(taskUpdated);
        convertToGlobalDate(taskUpdated);
        taskUpdated = taskRepository.save(validateStatus(taskUpdated));
        convertToLocalDate(taskUpdated);
        return taskUpdated;
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

    private void convertToLocalDate(Task task) {
        ZonedDateTime zoneGlobal = task.getDatetimeLimit().atZone(ZoneId.of("GMT"));
        ZonedDateTime zoneLocal = zoneGlobal.withZoneSameInstant(ZoneId.systemDefault());
        task.setDatetimeLimit(zoneLocal.toLocalDateTime());
    }

    private void convertToGlobalDate(Task task) {
        ZonedDateTime zoneLocal = task.getDatetimeLimit().atZone(ZoneId.systemDefault());
        ZonedDateTime zoneGlobal = zoneLocal.withZoneSameInstant(ZoneId.of("GMT"));
        task.setDatetimeLimit(zoneGlobal.toLocalDateTime());
    }
}
