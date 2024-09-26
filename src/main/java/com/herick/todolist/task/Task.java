package com.herick.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Priority priority;
    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle() throws Exception {
        if(title.length() > 50) {
            throw new Exception("The title field must contain a maximum of 50 characters");
        }
        this.title = title;
    }
}
