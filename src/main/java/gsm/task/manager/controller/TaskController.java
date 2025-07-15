package gsm.task.manager.controller;

import gsm.task.manager.domain.model.Task;
import gsm.task.manager.domain.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @Operation(summary = "Criar nova tarefa")
    public ResponseEntity<Task> createTask(@RequestBody Task taskToCreate) {
        Task taskCreated = taskService.createTask(taskToCreate);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskCreated.getId())
                .toUri();
        return ResponseEntity.created(uri).body(taskCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
        Task taskFound = taskService.findTaskById(id);
        return ResponseEntity.ok(taskFound);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task taskUpdated = taskService.updateTask(id, task);
        return ResponseEntity.ok(taskUpdated);
    }


}
