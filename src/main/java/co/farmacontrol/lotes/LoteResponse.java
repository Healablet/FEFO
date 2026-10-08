package co.farmacontrol.lotes;

import java.time.LocalDate;

public record LoteResponse(
        Long id,
        Long medicamentoId,
        String nombreMedicamento,
        String numeroLote,
        Integer cantidad,
        LocalDate fechaFabricacion,
        LocalDate fechaVencimiento
) {
    public static LoteResponse fromEntity(Lote lote) {
        return new LoteResponse(
                lote.getId(),
                lote.getMedicamento().getId(),
                lote.getMedicamento().getNombre(),
                lote.getNumeroLote(),
                lote.getCantidad(),
                lote.getFechaFabricacion(),
                lote.getFechaVencimiento()
        );
    }
}
