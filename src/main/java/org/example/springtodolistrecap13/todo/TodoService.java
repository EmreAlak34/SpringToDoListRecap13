package org.example.springtodolistrecap13.todo;

import org.example.springtodolistrecap13.todo.UpdateTodo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    private final de.neuefische.todobackend.todo.TodoRepository todoRepository;
    private final de.neuefische.todobackend.todo.IdService idService;
    private final de.neuefische.todobackend.todo.ChatGPTService chatGPTService;

    public TodoService(de.neuefische.todobackend.todo.TodoRepository todoRepository, de.neuefische.todobackend.todo.IdService idService, de.neuefische.todobackend.todo.ChatGPTService chatGPTService) {
        this.todoRepository = todoRepository;
        this.idService = idService;
        this.chatGPTService = chatGPTService;
    }

    public List<de.neuefische.todobackend.todo.Todo> findAllTodos() {
        return todoRepository.findAll();
    }

    public de.neuefische.todobackend.todo.Todo addTodo(de.neuefische.todobackend.todo.NewTodo newTodo) {
        // Rechtschreibprüfung
        String correctedDescription = chatGPTService.checkSpelling(newTodo.description());

        String id = idService.randomId();
        de.neuefische.todobackend.todo.Todo todoToSave = new de.neuefische.todobackend.todo.Todo(id, correctedDescription, newTodo.status());

        return todoRepository.save(todoToSave);
    }

    public de.neuefische.todobackend.todo.Todo updateTodo(UpdateTodo todo, String id) {
        String correctedDescription = chatGPTService.checkSpelling(todo.description());

        de.neuefische.todobackend.todo.Todo todoToUpdate = new de.neuefische.todobackend.todo.Todo(id, correctedDescription, todo.status());
        return todoRepository.save(todoToUpdate);
    }

    public de.neuefische.todobackend.todo.Todo findTodoById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo with id: " + id + " not found!"));
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }
}
