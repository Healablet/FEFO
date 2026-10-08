package co.farmacontrol.lotes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import co.farmacontrol.medicamentos.Medicamento;
import co.farmacontrol.medicamentos.MedicamentoRepository;
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
class LoteControllerIntegrationTest {

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
    void shouldRegisterAndReturnLotsOrderedByExpirationDate() throws Exception {
        Medicamento medicamento = medicamentoRepository.save(
                new Medicamento("Ibuprofeno", "Ibuprofeno", "400 mg", "Capsulas", 30)
        );

        mockMvc.perform(post("/api/lotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "medicamentoId": %d,
                                  "numeroLote": "LOT-2026-02",
                                  "cantidad": 120,
                                  "fechaFabricacion": "2026-01-15",
                                  "fechaVencimiento": "2026-12-10"
                                }
                                """.formatted(medicamento.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroLote").value("LOT-2026-02"));

        mockMvc.perform(post("/api/lotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "medicamentoId": %d,
                                  "numeroLote": "LOT-2026-01",
                                  "cantidad": 80,
                                  "fechaFabricacion": "2025-11-01",
                                  "fechaVencimiento": "2026-06-15"
                                }
                                """.formatted(medicamento.getId())))
                .andExpect(status().isCreated());

        assertThat(loteRepository.findByMedicamentoIdOrderByFechaVencimientoAsc(medicamento.getId()))
                .extracting(Lote::getNumeroLote)
                .containsExactly("LOT-2026-01", "LOT-2026-02");

        mockMvc.perform(get("/api/medicamentos/{medicamentoId}/lotes/fefo", medicamento.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroLote").value("LOT-2026-01"))
                .andExpect(jsonPath("$[1].numeroLote").value("LOT-2026-02"));
    }
}
