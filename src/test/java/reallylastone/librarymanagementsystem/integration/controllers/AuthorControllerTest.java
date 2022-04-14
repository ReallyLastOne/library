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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorControllerTest {
    private static final String CORRECT_AUTHOR_JSON = """
                        {
                            "fName": "John",
                            "lName": "Doe",
                            "books": []                          
                        }
            """;


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getAuthorsWhenUnAuthenticatedThenOk() throws Exception {
        this.mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getAuthorWhenUnAuthenticatedThenNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @Order(3)
    void addAuthorWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(post("/api/v1/authors").contentType("application/json").content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    void updateAuthorWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(put("/api/v1/authors/1").contentType("application/json").content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(5)
    void deleteAuthorWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(6)
    void addAuthorWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(post("/api/v1/authors").contentType("application/json").content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(7)
    void updateAuthorWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(put("/api/v1/authors/1").contentType("application/json").content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(8)
    void deleteAuthorWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(9)
    void addAuthorWhenModeratorThenCreated() throws Exception {
        this.mockMvc.perform(post("/api/v1/authors").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(10)
    void updateAuthorWhenModeratorThenOk() throws Exception {
        this.mockMvc.perform(put("/api/v1/authors/6").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(11)
    void deleteAuthorWhenModeratorThenForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/v1/authors/6"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(12)
    void addAuthorWhenAdminThenConflict() throws Exception {
        // Conflict since Moderator already created book. This is the reason for @Order annotation.
        this.mockMvc.perform(post("/api/v1/authors").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isConflict());
    }


    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(13)
    void updateAuthorWhenAdminThenOk() throws Exception {
        this.mockMvc.perform(put("/api/v1/authors/6").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(14)
    void deleteAuthorWhenAdminThenOk() throws Exception {
        this.mockMvc.perform(delete("/api/v1/authors/6"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    void patchAuthorWhenUnAuthenticatedThenUnauthorized() throws Exception {
        this.mockMvc.perform(patch("/api/v1/authors/1").contentType("application/json").content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    @Order(16)
    void patchAuthorWhenUserThenForbidden() throws Exception {
        this.mockMvc.perform(patch("/api/v1/authors/1`").contentType("application/json").content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "moderator", authorities = {"USER", "MODERATOR"})
    @Test
    @Order(17)
    void patchAuthorWhenModeratorThenNoContent() throws Exception {
        this.mockMvc.perform(post("/api/v1/authors").contentType("application/json").content(CORRECT_AUTHOR_JSON));
        this.mockMvc.perform(patch("/api/v1/authors/7").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin", authorities = {"USER", "MODERATOR", "ADMIN"})
    @Test
    @Order(18)
    void patchAuthorWhenAdminThenNoContent() throws Exception {
        this.mockMvc.perform(patch("/api/v1/authors/7").contentType("application/json").content(CORRECT_AUTHOR_JSON))
                .andExpect(status().isNoContent());
    }
}
