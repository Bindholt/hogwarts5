package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    void notNull() {
        assertNotNull(webClient);
    }

    @Test
    void createStudentWithFullName() {
        webClient
                .post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "name": "Julie Victoria Radant",
                        "house": "Gryffindor",
                        "schoolYear": 2
                    }
                """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.firstName").isEqualTo("Julie")
                .jsonPath("$.middleName").isEqualTo("Victoria")
                .jsonPath("$.lastName").isEqualTo("Radant")
                .jsonPath("$.fullName").isEqualTo("Julie Victoria Radant")
                .jsonPath("$.house").isEqualTo("Gryffindor")
                .jsonPath("$.schoolYear").isEqualTo(2);

    }

    @Test
    void createStudentWithNameParts() {
        webClient
                .post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Julie",
                        "middleName": "Victoria",
                        "lastName": "Radant",
                        "house": "Gryffindor",
                        "schoolYear": 2
                    }
                """)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(StudentResponseDTO.class)
                .value(student -> {
                    assertNotNull(student.id());
                    assertEquals("Julie", student.firstName());
                    assertEquals("Victoria", student.middleName());
                    assertEquals("Radant", student.lastName());
                    assertEquals("Julie Victoria Radant", student.fullName());
                    assertEquals("Gryffindor", student.house());
                    assertEquals(2, student.schoolYear());
                });
    }
}
