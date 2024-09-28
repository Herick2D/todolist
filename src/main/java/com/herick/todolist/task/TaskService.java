package com.herick.todolist.task;

import com.herick.todolist.user.UserDTO;
import com.herick.todolist.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserService userService;

    private TaskDTO createDTO(Task task) {
        TaskDTO newTaskDTO = new TaskDTO();
        newTaskDTO.setId(task.getId());
        newTaskDTO.setTitle(task.getTitle());
        newTaskDTO.setDescription(task.getDescription());
        newTaskDTO.setStartAt(task.getStartAt());
        newTaskDTO.setEndAt(task.getEndAt());
        newTaskDTO.setPriority(task.getPriority());
        return newTaskDTO;
    }

    public ResponseEntity<TaskDTO> createTask(Task task) {
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt())) {
            try {
                throw new BadRequestException("The task's start date cannot be in the past.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        UserDTO checkUser = userService.getUserById(task.getUserId()).getBody();
        if (checkUser == null) {
            throw new EntityNotFoundException("User with ID " + task.getUserId() + " not found.");
        }
        var toDTO = taskRepository.save(task);
        var result = createDTO(toDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    public ResponseEntity<Task> getTaskById(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
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

    public ResponseEntity<TaskDTO> updateTask(UUID taskId, Task task) {
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
        var toDTO = taskRepository.save(optinalTask);
        var result = createDTO(toDTO);
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
