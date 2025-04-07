package ro.unibuc.hello.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("IntegrationTest")
public class UserControllerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setupUser() throws Exception {
        String newUserJson = """
            {
                "id": "101",
                "nume": "Popescu",
                "prenume": "Andrei",
                "email": "andrei@example.com"
            }
        """;

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isOk());
    }

    @Test
@Order(0)
public void shouldCreateUser() throws Exception {
    String newUserJson = """
        {
            "id": "999",
            "nume": "Test",
            "prenume": "Case",
            "email": "test.case@example.com"
        }
    """;

    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newUserJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nume").value("Test"))
            .andExpect(jsonPath("$.email").value("test.case@example.com"));
}


    @Test
    @Order(1)
    public void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    public void shouldReturnUserById() throws Exception {
        mockMvc.perform(get("/api/user/101"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nume").value("Popescu"));
    }

    @Test
    @Order(3)
    public void shouldUpdateUser() throws Exception {
        String updatedUserJson = """
            {
                "id": "101",
                "nume": "Ionescu",
                "prenume": "Alexandra",
                "email": "alexandra@example.com"
            }
        """;

        mockMvc.perform(put("/api/user/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nume").value("Ionescu"));
    }

    @Test
    @Order(4)
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/101"))
                .andExpect(status().isOk());
    }
}
