package com.herick.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;


public record TaskDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Priority priority
) {
}
