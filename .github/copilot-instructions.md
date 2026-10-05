# Instrucciones del proyecto

- Mantén el alcance en el inventario farmacéutico: medicamentos, lotes, vencimientos con FEFO y alertas de reorden.
- Implementa una funcionalidad pequeña por rama y acompáñala con pruebas; usa commits descriptivos en español o Conventional Commits.
- Conserva Java 21, Spring Boot, Maven y PostgreSQL. Usa Flyway para cambios de esquema; no uses generación automática del esquema en producción.
- Mantén secretos fuera del código y configura PostgreSQL mediante variables de entorno.
- Excluye facturación electrónica/DIAN, comercio electrónico/pagos e integración automática con proveedores.
