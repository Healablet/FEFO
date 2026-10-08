CREATE TABLE lotes (
    id BIGSERIAL PRIMARY KEY,
    numero_lote VARCHAR(50) NOT NULL,
    cantidad INTEGER NOT NULL,
    fecha_fabricacion DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    medicamento_id BIGINT NOT NULL,
    CONSTRAINT fk_lotes_medicamento FOREIGN KEY (medicamento_id) REFERENCES medicamentos (id)
);

CREATE INDEX idx_lotes_medicamento_vencimiento ON lotes (medicamento_id, fecha_vencimiento);
