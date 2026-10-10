package co.farmacontrol.medicamentos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import co.farmacontrol.lotes.Lote;
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

    @Test
    void shouldListMedicamentosAtOrBelowMinimumUsingOnlyNonExpiredLots() throws Exception {
        LocalDate today = LocalDate.now();
        Medicamento lowStock = medicamentoRepository.save(
                new Medicamento("Acetaminofen", "Acetaminofen", "500 mg", "Tabletas", 20)
        );
        Medicamento noStock = medicamentoRepository.save(
                new Medicamento("Aspirina", "Acido acetilsalicilico", "100 mg", "Tabletas", 5)
        );
        Medicamento enoughStock = medicamentoRepository.save(
                new Medicamento("Vitamina C", "Acido ascorbico", "500 mg", "Tabletas", 20)
        );

        loteRepository.save(new Lote("ACTUAL-1", 7, today.minusMonths(1), today.plusMonths(1), lowStock));
        loteRepository.save(new Lote("VENCIDO-1", 100, today.minusMonths(2), today.minusDays(1), lowStock));
        loteRepository.save(new Lote("ACTUAL-2", 25, today.minusMonths(1), today, enoughStock));

        mockMvc.perform(get("/api/medicamentos/alertas-reorden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].medicamentoId").value(lowStock.getId()))
                .andExpect(jsonPath("$[0].nombre").value("Acetaminofen"))
                .andExpect(jsonPath("$[0].stockActual").value(7))
                .andExpect(jsonPath("$[0].stockMinimo").value(20))
                .andExpect(jsonPath("$[1].medicamentoId").value(noStock.getId()))
                .andExpect(jsonPath("$[1].stockActual").value(0))
                .andExpect(jsonPath("$[1].stockMinimo").value(5));
    }
}
