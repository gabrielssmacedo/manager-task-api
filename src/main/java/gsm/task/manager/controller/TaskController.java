package gsm.task.manager.controller;

import gsm.task.manager.domain.dto.TaskRequestDTO;
import gsm.task.manager.domain.model.Task;
import gsm.task.manager.domain.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Visualizar todas as tarefas")
    public ResponseEntity<List<Task>> findAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/week")
    @Operation(summary = "Visualizar todas as tarefas da semana", description = "  ")
    public ResponseEntity<List<Task>> findWeeklyTasks() {
        List<Task> tasks = taskService.findWeeklyTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/today")
    @Operation(summary = "Visualizar todas as tarefas de hoje", description = "  ")
    public ResponseEntity<List<Task>> findTasksForToday() {
        List<Task> tasks = taskService.findTaskForToday();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/late")
    @Operation(summary = "Visualizar todas as tarefas atrasadas", description = "  ")
    public ResponseEntity<List<Task>> findTasksLate() {
        List<Task> tasks = taskService.findTasksLated();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visualizar tarefa por id")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
        Task taskFound = taskService.findTaskById(id);
        return ResponseEntity.ok(taskFound);
    }

    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "   ")
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequestDTO taskToCreate) {
        Task taskCreated = taskService.createTask(taskToCreate);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskCreated.getId())
                .toUri();
        return ResponseEntity.created(uri).body(taskCreated);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar campos de uma tarefa", description = "   ")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskToUpdate) {
        Task taskUpdated = taskService.updateTask(id, taskToUpdate);
        return ResponseEntity.ok(taskUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma tarefa pelo id")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
