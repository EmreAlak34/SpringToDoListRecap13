package org.example.springtodolistrecap13.todo;

import java.util.List;

public record Todo(
        String id,
        String description,
        de.neuefische.todobackend.todo.TodoStatus status
) {

}

