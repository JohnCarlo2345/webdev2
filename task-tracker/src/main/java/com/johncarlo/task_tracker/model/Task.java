package com.johncarlo.task_tracker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Task {
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be 2–100 characters")
    private String title;
    @Size(max = 500, message = "Description max 500 characters")
    private String description;
    private boolean completed = false;
    public Task() {}
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
