package com.herick.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT t FROM tasks t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Optional<Task> searchTasks(@Param("title") String title, @Param("description") String description);
}
