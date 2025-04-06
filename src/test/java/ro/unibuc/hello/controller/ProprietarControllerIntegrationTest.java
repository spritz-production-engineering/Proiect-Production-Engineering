package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ro.unibuc.hello.dto.Proprietar;
import ro.unibuc.hello.service.ProprietarService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Tag("IntegrationTest")
public class ProprietarControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20");

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        final String MONGO_URL = "mongodb://root:example@localhost:" + mongoDBContainer.getMappedPort(27017);
        registry.add("mongodb.connection.url", () -> MONGO_URL);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProprietarService proprietarService;

    @BeforeEach
    public void cleanUpAndAddTestData() {
        proprietarService.deleteAllProprietari();

        Proprietar p1 = new Proprietar("101", "Popescu", "Ion", "ion@email.com", "1980516223457");
        Proprietar p2 = new Proprietar("102", "Ionescu", "Maria", "maria@email.com", "1961209456784");

        proprietarService.createProprietar(p1);
        proprietarService.createProprietar(p2);
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
        Proprietar newP = new Proprietar("103", "Dumitrescu", "Mihaela", "miha@email.com", "5020316223457");

        mockMvc.perform(post("/api/proprietar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newP)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("103"))
                .andExpect(jsonPath("$.nume").value("Dumitrescu"));
    }

    @Test
    public void testUpdateProprietar() throws Exception {
        Proprietar updated = new Proprietar("101", "Popescu", "Ioan", "ioan@email.com", "1961234567890");

        mockMvc.perform(put("/api/proprietar/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenume").value("Ioan"));
    }

    @Test
    public void testDeleteProprietar() throws Exception {
        mockMvc.perform(delete("/api/proprietar/101"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/proprietar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value("102"));
    }
}
