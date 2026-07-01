package org.duckemporium.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DuckController.class)
class DuckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DuckService duckService;

    @Test
    void listAll_returnsDucks() throws Exception {
        when(duckService.findAll()).thenReturn(List.of(
                new DuckDto(1L, "Classic Yellow", "Classics", 499, "The one that started it all."),
                new DuckDto(2L, "Pirate Duck", "Themed", 699, "Arrr!")
        ));

        mockMvc.perform(get("/api/ducks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Classic Yellow"))
                .andExpect(jsonPath("$[0].category").value("Classics"))
                .andExpect(jsonPath("$[0].priceInCents").value(499))
                .andExpect(jsonPath("$[0].tagline").value("The one that started it all."));
    }

    @Test
    void listAll_emptyReturnsEmptyArray() throws Exception {
        when(duckService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/ducks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0));
    }
}
