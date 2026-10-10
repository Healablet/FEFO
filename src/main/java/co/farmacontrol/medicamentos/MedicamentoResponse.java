package co.farmacontrol.medicamentos;

public record MedicamentoResponse(
        Long id,
        String nombre,
        String principioActivo,
        String concentracion,
        String presentacion,
        Integer stockMinimo
) {
    public static MedicamentoResponse fromEntity(Medicamento medicamento) {
        return new MedicamentoResponse(
                medicamento.getId(),
                medicamento.getNombre(),
                medicamento.getPrincipioActivo(),
                medicamento.getConcentracion(),
                medicamento.getPresentacion(),
                medicamento.getStockMinimo()
        );
    }
}
