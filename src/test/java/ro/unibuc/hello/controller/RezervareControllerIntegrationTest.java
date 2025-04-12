package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

import ro.unibuc.hello.data.ApartamentEntity;
import ro.unibuc.hello.data.ApartamentRepository;
import ro.unibuc.hello.data.LocatieEntity;
import ro.unibuc.hello.data.LocatieRepository;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.data.ProprietarRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.RezervareDto;
import ro.unibuc.hello.service.RezervareService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("IntegrationTest")
public class RezervareControllerIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.20");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RezervareService rezervareService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocatieRepository locatieRepository;

    @Autowired
    private ProprietarRepository proprietarRepository;

    @Autowired
    private ApartamentRepository apartamentRepository;

    @Autowired
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private RezervareDto rezervare1, rezervare2;
    private UserEntity user1, user2;
    private LocatieEntity locatie1, locatie2;
    private ProprietarEntity proprietar1, proprietar2;
    private ApartamentEntity apartament1, apartament2;

    @BeforeEach
    public void initData() {
        rezervareService.deleteAllRezervare();

        rezervare1 = new RezervareDto("r1", "1", "u1",
                LocalDate.of(2025, 4, 10), LocalDate.of(2025, 4, 12));

        rezervare2 = new RezervareDto("r2", "1", "u2",
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 3));

        user1 = new UserEntity("u1", "Anda", "Mirela", "andamirela@test.com");
        
        user2 = new UserEntity("u2", "Baciu", "Rares", "raresb@test.com");

        locatie1 = new LocatieEntity("1", "Italy", "Rome", "Rose Street");

        locatie2 = new LocatieEntity("2", "Spain", "Barcelona", "Barcelona Street");

        proprietar1 = new ProprietarEntity("p1", "John", "Doe", "john@example.com", "5020601456786");

        proprietar2 = new ProprietarEntity("p2", "Popa", "Dorian", "dorian@example.com", "5020601456786");

        apartament1 = new ApartamentEntity("1", 4, "1", "p1", 27, 2, 3, 4, "Italy", "Rome");

        apartament2 = new ApartamentEntity("2", 4, "2", "p2", 13, 1, 2, 2, "Spain", "Barcelona");

        userRepository.save(user1);
        userRepository.save(user2);
        locatieRepository.save(locatie1);
        locatieRepository.save(locatie2);
        proprietarRepository.save(proprietar1);
        proprietarRepository.save(proprietar2);
        apartamentRepository.save(apartament1);
        apartamentRepository.save(apartament2);
        rezervareService.saveRezervare(rezervare1);
        rezervareService.saveRezervare(rezervare2);
    }

    @Test
    @Order(1)
    public void testGetReservationById() throws Exception {
        mockMvc.perform(get("/api/rezervare/r1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("r1"))
                .andExpect(jsonPath("$.user.id").value("u1"))
                .andExpect(jsonPath("$.apartament.id").value("1"));
    }

    @Test
    @Order(2)
    public void testGetReservationsByOwnerId() throws Exception {
        mockMvc.perform(get("/api/rezervare/proprietar/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(3)
    public void testGetReservationsByUserId() throws Exception {
        mockMvc.perform(get("/api/rezervare/user/u1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value("r1"));
    }

    @Test
    @Order(4)
    public void testCreateRezervare() throws Exception {
        RezervareDto newRez = new RezervareDto("r3", "2", "u2",
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 5));

        mockMvc.perform(post("/api/rezervare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRez)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("r3"))
                .andExpect(jsonPath("$.user.id").value("u2"))
                .andExpect(jsonPath("$.apartament.id").value("2"));
    }

    @Test
    @Order(5)
    public void testUpdateRezervare() throws Exception {
        mockMvc.perform(put("/api/rezervare/r2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("r2"));
    }

    @Test
    @Order(6)
    public void testDeleteRezervare() throws Exception {
        mockMvc.perform(delete("/api/rezervare/r1"))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
    }


    @Test
    @Order(7)
    public void testDeleteAllRezervari() throws Exception {
        mockMvc.perform(delete("/api/rezervare"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/rezervare/proprietar/r1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
