# Sistema de Inscripción a Eventos Universitarios 🎓

Este proyecto consiste en una API REST construida con **Spring Boot** para gestionar la inscripción de participantes a eventos académicos. El sistema está diseñado bajo principios de arquitectura limpia, asegurando la consistencia de los datos y la observabilidad del sistema.

## 🏗️ Arquitectura y Diseño

El proyecto sigue una **Arquitectura en Capas**, lo que garantiza la separación de responsabilidades y facilita el mantenimiento:

1.  **Capa de Modelo (Entities):** Representación de los datos mediante objetos con anotaciones JPA (`Evento` y `Participante`).
2.  **Capa de Repositorio (Persistence):** Uso de Spring Data JPA para la abstracción de la base de datos, eliminando la necesidad de escribir SQL manual.
3.  **Capa de Servicio (Business Logic):** Donde reside la lógica de negocio, validaciones de cupos y manejo de concurrencia.
4.  **Capa de Controlador (Web/REST):** Definición de los endpoints para la comunicación con clientes externos (Postman/Frontend).

---

## 🚀 Drivers Arquitectónicos

Para cumplir con los requerimientos del sistema, se priorizaron los siguientes atributos de calidad:

- **Consistencia (Driver Crítico):** Se utilizó la palabra clave `synchronized` en los métodos de inscripción y cancelación para evitar condiciones de carrera (race conditions) durante picos de alta demanda. Esto garantiza que nunca se exceda el cupo máximo de un evento.
- **Mantenibilidad:** Se implementó **Inyección de Dependencias** a través de constructores, facilitando las pruebas unitarias y permitiendo que los componentes sean desacoplados.
- **Observabilidad (Auditoría):** El sistema integra un sistema de trazabilidad por consola que registra cada operación crítica (inscripciones exitosas, fallidas y cancelaciones) para monitoreo.
- **Escalabilidad:** Al utilizar Spring Boot y una base de datos desacoplada, el sistema está listo para ser empaquetado en contenedores (Docker) y escalar según la carga.

---

## 💻 Paradigmas de Programación Aplicados

El código demuestra el dominio de tres paradigmas fundamentales en Java:

1.  **Programación Orientada a Objetos (POO):** Uso de clases, encapsulamiento y relaciones `@ManyToOne` entre entidades.
2.  **Paradigma Imperativo:** Utilizado en el método `inscribir` para controlar paso a paso el flujo de validación de cupos y persistencia de datos.
3.  **Paradigma Funcional (Declarativo):** Implementado mediante **Java Streams** en el método `listarInscritos`, utilizando filtros y colectores para procesar datos de forma legible y eficiente.

---

## 🧪 Pruebas Unitarias

Se incluyeron pruebas unitarias utilizando **JUnit 5** y **AssertJ** para validar la lógica de negocio.

- **Test de Validación de Cupo:** Verifica que el sistema arroje una `RuntimeException` cuando se intenta inscribir a un participante en un evento sin cupos disponibles, asegurando la integridad de las reglas de negocio.

---

## 🛠️ Cómo utilizar la API

### 1. Requisitos

- Java 17 o superior.
- Gradle (incluido en el proyecto).

### 2. Endpoints Principales

| Acción                     | Método   | Endpoint                           | Parámetros (Query/Path) |
| :------------------------- | :------- | :--------------------------------- | :---------------------- |
| **Crear Evento**           | `POST`   | `/api/inscripciones/eventos`       | `nombre`, `cupos`       |
| **Inscribir Participante** | `POST`   | `/api/inscripciones/registrar`     | `nombre`, `eventoId`    |
| **Listar Eventos**         | `GET`    | `/api/inscripciones/eventos`       | -                       |
| **Listar Inscritos**       | `GET`    | `/api/inscripciones/evento/{id}`   | `id` (del evento)       |
| **Cancelar Inscripción**   | `DELETE` | `/api/inscripciones/cancelar/{id}` | `id` (del participante) |

### 3. Ejecución

Para arrancar el proyecto desde la terminal:

```bash
./gradlew bootRun
```

Para ejecutar las pruebas unitarias

```bash
./gradlew test
```
