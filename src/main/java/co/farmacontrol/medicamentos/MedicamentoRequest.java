package co.farmacontrol.medicamentos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record MedicamentoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El principio activo es obligatorio")
        String principioActivo,

        @NotBlank(message = "La concentración es obligatoria")
        String concentracion,

        @NotBlank(message = "La presentación es obligatoria")
        String presentacion,

        @NotNull(message = "El stock mínimo es obligatorio")
        @PositiveOrZero(message = "El stock mínimo no puede ser negativo")
        Integer stockMinimo
) {
    public Medicamento toEntity() {
        return new Medicamento(nombre, principioActivo, concentracion, presentacion, stockMinimo);
    }
}
