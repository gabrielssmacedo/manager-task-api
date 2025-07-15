package gsm.task.manager.controller;

import gsm.task.manager.domain.model.SubTask;
import gsm.task.manager.domain.service.SubTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/subtasks")
@Tag(name = "SubTasks")

public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova subtarefa")
    public ResponseEntity<SubTask> createSubtask(@RequestBody SubTask subTask) {
        SubTask subTaskCreated = subTaskService.createSubtask(subTask);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subTaskCreated.getId())
                .toUri();
        return ResponseEntity.created(uri).body(subTaskCreated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma subtarefa pelo id")
    public ResponseEntity<Void> deleteSubtaskById(@PathVariable Long id) {
        subTaskService.deleteSubtask(id);
        return ResponseEntity.noContent().build();
    }
}
