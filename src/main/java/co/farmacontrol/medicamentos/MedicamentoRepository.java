package co.farmacontrol.medicamentos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    @Query("""
            select new co.farmacontrol.medicamentos.AlertaReordenResponse(
                m.id, m.nombre, coalesce(sum(l.cantidad), 0L), m.stockMinimo)
            from Medicamento m
            left join Lote l on l.medicamento = m and l.fechaVencimiento >= :fechaActual
            group by m.id, m.nombre, m.stockMinimo
            having coalesce(sum(l.cantidad), 0L) <= m.stockMinimo
            order by m.nombre
            """)
    List<AlertaReordenResponse> findAlertasReorden(@Param("fechaActual") LocalDate fechaActual);
}
