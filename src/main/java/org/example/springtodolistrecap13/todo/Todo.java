package org.example.springtodolistrecap13.todo;

public record Todo(
        String id,
        String description,
        de.neuefische.todobackend.todo.TodoStatus status
) {
}
