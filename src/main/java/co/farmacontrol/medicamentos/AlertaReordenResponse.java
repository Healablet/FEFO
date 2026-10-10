package co.farmacontrol.medicamentos;

public record AlertaReordenResponse(
        Long medicamentoId,
        String nombre,
        Long stockActual,
        Integer stockMinimo
) {
}
