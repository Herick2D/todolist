package com.herick.todolist.task;

import com.herick.todolist.user.User;
import com.herick.todolist.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserService userService;

    public ResponseEntity<Task> createTask(Task task) {
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt())) {
            try {
                throw new BadRequestException("The task's start date cannot be in the past.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        User checkUser = userService.getUserById(task.getUserId()).getBody();
        if (checkUser == null) {
            throw new EntityNotFoundException("User with ID " + task.getUserId() + " not found.");
        }

        var result = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    public ResponseEntity<Task> getTaskById(UUID taskId) {
        var optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new EntityNotFoundException("Task with ID " + taskId + " not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalTask.get());
    }

//    public ResponseEntity<List<Task>> searchTasks(String title, String description) {
//        Optional<Task> tasks = taskRepository.searchTasks(
//                title != null ? title : "",
//                description != null ? description : ""
//        );
//
//        if (tasks.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        List<Task> receivedTasks = new ArrayList<>();
//        receivedTasks.addAll(tasks);
//        return ResponseEntity.status(HttpStatus.OK).body(receivedTasks);
//    }

    public ResponseEntity<Task> updateTask(UUID taskId, Task task) {
        LocalDateTime currentDate = LocalDateTime.now();
        Task optinalTask = getTaskById(taskId).getBody();

        optinalTask.setTitle(task.getTitle());
        optinalTask.setDescription(task.getDescription());
        optinalTask.setPriority(task.getPriority());

        if (currentDate.isAfter(task.getEndAt())) {
            try {
                throw new BadRequestException("The task's start date cannot be in the past.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        optinalTask.setEndAt(task.getEndAt());
        var result = taskRepository.save(optinalTask);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<?> deleteTask(UUID taskId) {
        try {
            taskRepository.deleteById(taskId);
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
        return ResponseEntity.noContent().build();
    }


}
