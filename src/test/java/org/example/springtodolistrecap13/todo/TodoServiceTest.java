package de.neuefische.todobackend.todo;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    de.neuefische.todobackend.todo.TodoRepository todoRepository = mock(de.neuefische.todobackend.todo.TodoRepository.class);
    de.neuefische.todobackend.todo.IdService idService = mock(de.neuefische.todobackend.todo.IdService.class);
    de.neuefische.todobackend.todo.TodoService todoService = new de.neuefische.todobackend.todo.TodoService(todoRepository, idService);

    @Test
    void findAllTodos() {
        //GIVEN
        de.neuefische.todobackend.todo.Todo t1 = new de.neuefische.todobackend.todo.Todo("1", "d1", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        de.neuefische.todobackend.todo.Todo t2 = new de.neuefische.todobackend.todo.Todo("2", "d2", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        de.neuefische.todobackend.todo.Todo t3 = new de.neuefische.todobackend.todo.Todo("3", "d3", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        List<de.neuefische.todobackend.todo.Todo> todos = List.of(t1, t2, t3);

        when(todoRepository.findAll()).thenReturn(todos);

        //WHEN
        List<de.neuefische.todobackend.todo.Todo> actual = todoService.findAllTodos();

        //THEN

        verify(todoRepository).findAll();
        assertEquals(todos, actual);
    }

    @Test
    void addTodo() {
        //GIVEN
        de.neuefische.todobackend.todo.NewTodo newTodo = new de.neuefische.todobackend.todo.NewTodo("Test-Description", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        de.neuefische.todobackend.todo.Todo todoToSave = new de.neuefische.todobackend.todo.Todo("Test-Id", "Test-Description", de.neuefische.todobackend.todo.TodoStatus.OPEN);

        when(idService.randomId()).thenReturn("Test-Id");
        when(todoRepository.save(todoToSave)).thenReturn(todoToSave);

        //WHEN

        de.neuefische.todobackend.todo.Todo actual = todoService.addTodo(newTodo);

        //THEN
        verify(idService).randomId();
        verify(todoRepository).save(todoToSave);
        assertEquals(todoToSave, actual);
    }

    @Test
    void updateTodo() {
        //GIVEN
        String id = "123";
        UpdateTodo todoToUpdate = new UpdateTodo("test-description", de.neuefische.todobackend.todo.TodoStatus.IN_PROGRESS);

        de.neuefische.todobackend.todo.Todo updatedTodo = new de.neuefische.todobackend.todo.Todo("123", "test-description", de.neuefische.todobackend.todo.TodoStatus.IN_PROGRESS);

        when(todoRepository.save(updatedTodo)).thenReturn(updatedTodo);

        //WHEN

        de.neuefische.todobackend.todo.Todo actual = todoService.updateTodo(todoToUpdate, id);

        //THEN
        verify(todoRepository).save(updatedTodo);

        assertEquals(updatedTodo, actual);
    }

    @Test
    void getTodoByIdTest_whenValidId_ThenReturnTodo() {
        //GIVEN
        String id = "1";
        de.neuefische.todobackend.todo.Todo todo = new de.neuefische.todobackend.todo.Todo("1", "test-description", de.neuefische.todobackend.todo.TodoStatus.OPEN);

        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));

        //WHEN

        de.neuefische.todobackend.todo.Todo actual = todoService.findTodoById(id);

        //THEN
        verify(todoRepository).findById(id);
        assertEquals(todo, actual);
    }

    @Test
    void getTodoByIdTest_whenInvalidId_ThenThrowException() {
        //GIVEN
        String id = "1";

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN

        assertThrows(NoSuchElementException.class, () -> todoService.findTodoById(id));

        //THEN
        verify(todoRepository).findById(id);
    }

    @Test
    void deleteTodo() {
        //GIVEN
        String id = "1";
        doNothing().when(todoRepository).deleteById(id);

        //WHEN

        todoService.deleteTodo(id);

        //THEN
        verify(todoRepository).deleteById(id);
    }
}
