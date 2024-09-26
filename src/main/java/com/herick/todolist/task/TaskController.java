package com.herick.todolist.task;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping({"/task", "/tasks"})
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ResponseEntity<Task>> createTask(@RequestBody Task task) {
        var resultado = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseEntity<Task>> updateTask(@PathVariable UUID taskId, @RequestBody Task task) {
        var resultado = taskService.updateTask(taskId, task);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID taskId) {
        return taskService.deleteTask(taskId);
    }
}
