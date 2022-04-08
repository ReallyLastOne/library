package reallylastone.librarymanagementsystem.integration.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {
    private static final String CORRECT_BOOK_JSON = """
            {
                 "ISBN": 1225,
                 "author": {
                     "fname": "john",
                     "lname": "doe"
                 },
                 "title": "title"
             }
            """;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getBooksWhenUnAuthenticatedThenOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getBookWhenUnAuthenticatedThenNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    void addBookWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(post("/api/v1/books").contentType("application/json").content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    void updateBookWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(put("/api/v1/books/1").contentType("application/json").content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void deleteBookWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(delete("/api/v1/books/1225"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(6)
    void addBookWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(post("/api/v1/books").contentType("application/json").content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(7)
    void updateBookWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(put("/api/v1/books/1").contentType("application/json").content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(8)
    void deleteBookWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(9)
    void addBookWhenModeratorThenCreated() throws Exception {
        this.mockMvc.perform(post("/api/v1/books").contentType("application/json").content(CORRECT_BOOK_JSON))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(10)
    void updateBookWhenModeratorThenOk() throws Exception {
        this.mockMvc.perform(put("/api/v1/books/1225").contentType("application/json").content(CORRECT_BOOK_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(11)
    void deleteBookWhenModeratorThenForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/v1/books/1225"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(12)
    void addBookWhenAdminThenConflict() throws Exception {
        // Conflict since Moderator already created book. This is the reason for @Order annotation.
        this.mockMvc.perform(post("/api/v1/books").contentType("application/json").content(CORRECT_BOOK_JSON))
                .andExpect(status().isConflict());
    }


    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(13)
    void updateBookWhenAdminThenOk() throws Exception {
        this.mockMvc.perform(put("/api/v1/books/1225").contentType("application/json").content(CORRECT_BOOK_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(14)
    void deleteBookWhenAdminThenOk() throws Exception {
        this.mockMvc.perform(delete("/api/v1/books/1225"))
                .andExpect(status().isOk());
    }
}

