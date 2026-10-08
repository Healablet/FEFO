package co.farmacontrol.lotes;

import java.net.URI;
import java.util.List;

import co.farmacontrol.medicamentos.Medicamento;
import co.farmacontrol.medicamentos.MedicamentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class LoteController {

    private final LoteRepository loteRepository;
    private final MedicamentoRepository medicamentoRepository;

    public LoteController(LoteRepository loteRepository, MedicamentoRepository medicamentoRepository) {
        this.loteRepository = loteRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @PostMapping("/lotes")
    public ResponseEntity<LoteResponse> createLote(@Valid @RequestBody LoteRequest request) {
        Medicamento medicamento = medicamentoRepository.findById(request.medicamentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicamento no encontrado"));

        Lote saved = loteRepository.save(request.toEntity(medicamento));
        return ResponseEntity.created(URI.create("/api/lotes/" + saved.getId()))
                .body(LoteResponse.fromEntity(saved));
    }

    @GetMapping("/medicamentos/{medicamentoId}/lotes/fefo")
    public List<LoteResponse> listLotsByFefo(@PathVariable Long medicamentoId) {
        return loteRepository.findByMedicamentoIdOrderByFechaVencimientoAsc(medicamentoId)
                .stream()
                .map(LoteResponse::fromEntity)
                .toList();
    }
}
