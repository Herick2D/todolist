package com.herick.todolist.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskDTO {

    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Priority priority;
    private UUID userId;

}
