CREATE TABLE medicamentos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    principio_activo VARCHAR(150) NOT NULL,
    concentracion VARCHAR(50) NOT NULL,
    presentacion VARCHAR(80) NOT NULL,
    stock_minimo INTEGER NOT NULL
);

CREATE INDEX idx_medicamentos_nombre ON medicamentos (nombre);
