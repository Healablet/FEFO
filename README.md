# FarmaControl

Base de una aplicación web para gestionar inventario farmacéutico en una droguería. El objetivo del proyecto es reducir pérdidas por vencimientos y evitar desabastecimientos mediante control FEFO y alertas de reorden.

## Alcance inicial

Este primer incremento contiene el esqueleto Spring Boot, una comprobación de salud y la conexión preparada para PostgreSQL. Todavía no implementa productos, lotes, reglas FEFO ni alertas.

**Fuera del alcance:** facturación electrónica e integración con la DIAN, e-commerce/pasarela de pagos y pedidos automáticos por API a proveedores.

## Requisitos

- Java 21
- Maven 3.9 o superior
- Docker Desktop (para iniciar PostgreSQL localmente)

## Ejecutar localmente

1. Inicia la base de datos: `docker compose up -d postgres`.
2. Inicia la aplicación: `mvn spring-boot:run`.
3. Comprueba el servicio en `http://localhost:8080/api/health`.
4. Ejecuta las pruebas con `mvn test`.

La configuración local está en `compose.yaml`. La aplicación acepta `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` como variables de entorno. Los valores incluidos son únicamente para desarrollo local; no uses credenciales reales en el repositorio.

## Trabajo por incrementos, ramas y commits

Trabaja una tarea pequeña por rama y abre una solicitud de integración hacia `main` al terminarla. Ejemplos de ramas:

1. `feat/catalogo-medicamentos` — registrar y consultar medicamentos.
2. `feat/lotes-fefo` — registrar lotes y priorizar por vencimiento (First Expired, First Out).
3. `feat/alertas-vencimiento` — semaforizar lotes próximos a vencer.
4. `feat/alertas-reorden` — calcular el punto de reorden y señalar stock crítico.

Ejemplos de commits pequeños: `chore: crear base Spring Boot`, `feat: agregar endpoint de salud`, `test: cubrir endpoint de salud`. Haz commits después de verificar cada cambio; no combines funcionalidades distintas en un solo commit.

## Tecnologías

Java 21, Spring Boot, Spring Web, Spring Data JPA, Bean Validation, Flyway y PostgreSQL.
