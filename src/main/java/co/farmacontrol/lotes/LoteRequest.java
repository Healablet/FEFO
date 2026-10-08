package co.farmacontrol.lotes;

import java.time.LocalDate;

import co.farmacontrol.medicamentos.Medicamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoteRequest(
        @NotNull(message = "El medicamento es obligatorio")
        Long medicamentoId,

        @NotBlank(message = "El número de lote es obligatorio")
        String numeroLote,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a cero")
        Integer cantidad,

        @NotNull(message = "La fecha de fabricación es obligatoria")
        LocalDate fechaFabricacion,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDate fechaVencimiento
) {
    public Lote toEntity(Medicamento medicamento) {
        return new Lote(numeroLote, cantidad, fechaFabricacion, fechaVencimiento, medicamento);
    }
}
