package dk.kea.dat3js.hogwarts5.ghost;

import dk.kea.dat3js.hogwarts5.house.House;
import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(GhostController.class)
@ComponentScan(basePackageClasses = {HouseService.class})
public class GhostTest {

    @MockBean
    private HouseRepository houseRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        var gryffindor= new House("Gryffindor", "Godric Gryffindor", new String[]{"red", "gold"});
        var ravenclaw = new House("Ravenclaw", "Rowena Ravenclaw", new String[]{"blue", "silver"});
        var hufflepuff = new House("Hufflepuff", "Helga Hufflepuff", new String[]{"yellow", "black"});
        var slytherin = new House("Slytherin", "Salazar Slytherin", new String[]{"green", "silver"});

        when(houseRepository.findById("Gryffindor")).thenReturn(Optional.of(gryffindor));
        when(houseRepository.findById("Ravenclaw")).thenReturn(Optional.of(ravenclaw));
        when(houseRepository.findById("Hufflepuff")).thenReturn(Optional.of(hufflepuff));
        when(houseRepository.findById("Slytherin")).thenReturn(Optional.of(slytherin));
    }

    @Test
    void getAllGhosts() throws Exception {
        mockMvc.perform(get("/ghosts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("Nearly Headless Nick", "The Grey Lady", "The Fat Friar", "The Bloody Baron") ));
    }

    @Test
    void getGhostByName() throws Exception{
        mockMvc.perform(get("/ghosts/Nearly Headless Nick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nearly Headless Nick"))
                .andExpect(jsonPath("$.realName").value("Sir Nicholas de Mimsy-Porpington"))
                .andExpect(jsonPath("$.house.name").value("Gryffindor"));
    }
}
