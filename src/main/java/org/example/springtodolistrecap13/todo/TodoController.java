package org.example.springtodolistrecap13.todo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final de.neuefische.todobackend.todo.TodoService todoService;

    public TodoController(de.neuefische.todobackend.todo.TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.findAllTodos();
    }

    @GetMapping("{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.findTodoById(id);
    }

    @PostMapping
    public Todo postTodo(@RequestBody NewTodo newTodo) {
        return todoService.addTodo(newTodo);
    }

    @PutMapping("{id}")
    public Todo putTodo(@RequestBody UpdateTodo todo, @PathVariable String id) {
        return todoService.updateTodo(todo, id);
    }

    @DeleteMapping("{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
    }
}
