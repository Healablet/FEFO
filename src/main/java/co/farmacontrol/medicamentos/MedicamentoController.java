package co.farmacontrol.medicamentos;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping("/medicamentos")
    public List<MedicamentoResponse> listMedicamentos() {
        return medicamentoRepository.findAll().stream()
                .map(MedicamentoResponse::fromEntity)
                .toList();
    }

    @GetMapping("/medicamentos/alertas-reorden")
    public List<AlertaReordenResponse> listAlertasReorden() {
        return medicamentoRepository.findAlertasReorden(LocalDate.now());
    }

    @PostMapping("/medicamentos")
    public ResponseEntity<MedicamentoResponse> createMedicamento(@Valid @RequestBody MedicamentoRequest request) {
        Medicamento saved = medicamentoRepository.save(request.toEntity());
        MedicamentoResponse response = MedicamentoResponse.fromEntity(saved);
        return ResponseEntity.created(URI.create("/api/medicamentos/" + saved.getId())).body(response);
    }
}
