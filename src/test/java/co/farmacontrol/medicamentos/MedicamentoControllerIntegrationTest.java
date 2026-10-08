package co.farmacontrol.medicamentos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.farmacontrol.lotes.LoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MedicamentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private LoteRepository loteRepository;

    @BeforeEach
    void cleanData() {
        loteRepository.deleteAll();
        medicamentoRepository.deleteAll();
    }

    @Test
    void shouldCreateAndListMedicamentos() throws Exception {
        mockMvc.perform(post("/api/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Paracetamol",
                                  "principioActivo": "Acetaminofén",
                                  "concentracion": "500 mg",
                                  "presentacion": "Tabletas",
                                  "stockMinimo": 25
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Paracetamol"));

        var medicamentos = medicamentoRepository.findAll();
        assertThat(medicamentos).hasSize(1);

        mockMvc.perform(get("/api/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Paracetamol"));
    }
}
