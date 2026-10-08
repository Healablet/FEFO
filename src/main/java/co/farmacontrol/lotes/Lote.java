package co.farmacontrol.lotes;

import java.time.LocalDate;

import co.farmacontrol.medicamentos.Medicamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotes")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_lote", nullable = false, length = 50)
    private String numeroLote;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_fabricacion", nullable = false)
    private LocalDate fechaFabricacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    protected Lote() {
    }

    public Lote(String numeroLote, Integer cantidad, LocalDate fechaFabricacion,
                LocalDate fechaVencimiento, Medicamento medicamento) {
        this.numeroLote = numeroLote;
        this.cantidad = cantidad;
        this.fechaFabricacion = fechaFabricacion;
        this.fechaVencimiento = fechaVencimiento;
        this.medicamento = medicamento;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public LocalDate getFechaFabricacion() {
        return fechaFabricacion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }
}
