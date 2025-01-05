package org.example.springtodolistrecap13.todo;

public record NewTodo(
        String description,
        de.neuefische.todobackend.todo.TodoStatus status
) {
}
