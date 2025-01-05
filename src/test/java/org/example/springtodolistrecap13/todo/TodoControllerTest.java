package de.neuefische.todobackend.todo;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    de.neuefische.todobackend.todo.TodoRepository todoRepository;

    @Test
    void getAllTodos() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            []
                        """));

    }

    @Test
    @DirtiesContext
    void postTodo() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "description": "test-description",
                                        "status": "OPEN"
                                    }
                                """)
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "description": "test-description",
                                "status": "OPEN"
                            }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void putTodo() throws Exception {
        //GIVEN
        de.neuefische.todobackend.todo.Todo existingTodo = new de.neuefische.todobackend.todo.Todo("1", "test-description", de.neuefische.todobackend.todo.TodoStatus.OPEN);

        todoRepository.save(existingTodo);

        //WHEN
        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "description": "test-description-2",
                                        "status": "IN_PROGRESS"
                                    }
                                """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "id": "1",
                                "description": "test-description-2",
                                "status": "IN_PROGRESS"
                            }
                        """));
    }

    @Test
    @DirtiesContext
    void getById() throws Exception {
        //GIVEN
        de.neuefische.todobackend.todo.Todo existingTodo = new de.neuefische.todobackend.todo.Todo("1", "test-description", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        todoRepository.save(existingTodo);

        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                            {
                                "id": "1",
                                "description": "test-description",
                                "status": "OPEN"
                            }
                        """));

    }

    @Test
    @DirtiesContext
    void getByIdTest_whenInvalidId_thenStatus404() throws Exception {
        //GIVEN
        //WHEN

        assertThrows(ServletException.class, () -> mockMvc.perform(get("/api/todo/1")));

    }


    @Test
    @DirtiesContext
    void deleteTodoById() throws Exception {
        //GIVEN
        de.neuefische.todobackend.todo.Todo existingTodo = new de.neuefische.todobackend.todo.Todo("1", "test-description", de.neuefische.todobackend.todo.TodoStatus.OPEN);
        todoRepository.save(existingTodo);

        //WHEN
        mockMvc.perform(delete("/api/todo/1"))
                //THEN
                .andExpect(status().isOk());
    }
}
