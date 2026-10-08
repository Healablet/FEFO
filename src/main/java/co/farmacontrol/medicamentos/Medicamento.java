package co.farmacontrol.medicamentos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(name = "principio_activo", nullable = false, length = 150)
    private String principioActivo;

    @Column(nullable = false, length = 50)
    private String concentracion;

    @Column(nullable = false, length = 80)
    private String presentacion;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    protected Medicamento() {
    }

    public Medicamento(String nombre, String principioActivo, String concentracion,
                       String presentacion, Integer stockMinimo) {
        this.nombre = nombre;
        this.principioActivo = principioActivo;
        this.concentracion = concentracion;
        this.presentacion = presentacion;
        this.stockMinimo = stockMinimo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }
}
