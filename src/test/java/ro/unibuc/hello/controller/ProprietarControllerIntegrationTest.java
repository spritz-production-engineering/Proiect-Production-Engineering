package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.service.ProprietarService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class ProprietarControllerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20");

    @BeforeAll
    public static void setup() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProprietarService proprietarService;

    private String p1Id;
    private String p2Id;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        proprietarService.deleteAllProprietari();

        Proprietar p1 = new Proprietar(null, "Popescu", "Ion", "ion@email.com", "5020601456786");
        Proprietar p2 = new Proprietar(null, "Ionescu", "Maria", "maria@email.com", "5020601456786");

        p1Id = proprietarService.createProprietar(p1).getId();
        p2Id = proprietarService.createProprietar(p2).getId();
    }

    @Test
    public void testGetAllProprietari() throws Exception {
        mockMvc.perform(get("/api/proprietar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nume").value("Popescu"))
                .andExpect(jsonPath("$[1].nume").value("Ionescu"));
    }

    @Test
    public void testCreateProprietar() throws Exception {
        Proprietar newP = new Proprietar(null, "Dumitrescu", "Mihaela", "miha@email.com", "5020601456786");

        mockMvc.perform(post("/api/proprietar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newP)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nume").value("Dumitrescu"))
                .andExpect(jsonPath("$.prenume").value("Mihaela"))
                .andExpect(jsonPath("$.email").value("miha@email.com"));
    }

    @Test
    public void testUpdateProprietar() throws Exception {
        Proprietar updated = new Proprietar(p1Id, "Popescu", "Ioan", "ioan@email.com", "5020601456786");

        mockMvc.perform(put("/api/proprietar/" + p1Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenume").value("Ioan"))
                .andExpect(jsonPath("$.email").value("ioan@email.com"));
    }

    @Test
    public void testDeleteProprietar() throws Exception {
        mockMvc.perform(delete("/api/proprietar/" + p1Id))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/proprietar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(p2Id));
    }
}
