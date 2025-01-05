package de.neuefische.todobackend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleNoSuchElementException() throws Exception {
        mockMvc.perform(get("/api/todo/invalid-id"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Todo with id: invalid-id not found!"));
    }

    @Test
    public void testHandleGeneralException() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isInternalServerError());
    }
}
