# Topaz Challenge

API REST para reversar transacciones aprobadas usando Spring Boot, Spring Data JPA y una base de datos H2 en memoria.

## Requisitos

- Java 21
- No es necesario instalar Maven: el proyecto incluye Maven Wrapper.

## Ejecutar

En Windows PowerShell, desde la raíz del proyecto:

```powershell
& (Join-Path $PWD 'mvnw.cmd') spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La aplicación inicia en `http://localhost:8080`. Para ejecutar las pruebas:

```powershell
& (Join-Path $PWD 'mvnw.cmd') test
```

## API

### Reversar una transacción

```http
PATCH /v1/transacciones/{id}/reversar
```

No requiere cuerpo de solicitud. Ejemplo:

```bash
curl -i -X PATCH http://localhost:8080/v1/transacciones/1/reversar
```

La operación solo se realiza si la transacción existe, está en estado `APROBADA` y fue creada dentro de las últimas 24 horas. Si se cumplen las condiciones, cambia su estado a `REVERSADA` y responde `204 No Content`.

| Estado HTTP | Descripción |
| --- | --- |
| `204` | Transacción reversada correctamente. |
| `404` | No existe una transacción con ese identificador. |
| `409` | La transacción no está `APROBADA` o fue modificada concurrentemente. |
| `422` | La transacción supera el plazo de 24 horas para ser reversada. |

Los errores devuelven un JSON con el campo `message`, por ejemplo: `{"message":"Transaccion no encontrada"}`.

## Persistencia

La entidad `Transaccion` contiene `id`, `monto`, `estado`, `fechaCreacion` y `version`. Los estados admitidos son `CREADA`, `PENDIENTE`, `APROBADA`, `RECHAZADA` y `REVERSADA`. El campo de versión permite detectar modificaciones concurrentes.

La base H2 está configurada como base de datos en memoria (`jdbc:h2:mem:testdb`), por lo que los datos no persisten al reiniciar la aplicación. La API de este proyecto solo expone la operación de reversa; para probarla, debe existir previamente una transacción en la base de datos.
