# Briefing: migrar la API manual al servidor generado desde Smithy

Instrucciones para una IA o desarrollador que deba **sustituir la capa REST escrita a mano** por el **servidor Java generado desde Smithy** (OpenAPI Generator, Spring + `delegatePattern`) y **eliminar** el código manual equivalente cuando la integración compile y pasen las pruebas.

---

## Objetivo

Integrar el código generado en `smithy/build/generated/spring/` en la aplicación Maven principal, enlazar **delegates** con la lógica existente (`service`, DAO, JPA), y borrar controladores/DTOs duplicados una vez verificado el comportamiento.

---

## Contexto del repositorio

| Área | Ubicación |
|------|-----------|
| App Spring Boot actual | `src/main/java/com/githubx/githubfilesms/` (controladores, servicios, JPA, seguridad JWT, etc.) |
| Contrato Smithy | Carpeta `smithy/` (`model/`, `smithy-build.json`, `build.gradle`) |
| Servicio modelado | `com.github.files#FilesApi`, proyección `github-files` |

Tras generar, el servidor Spring suele quedar en:

`smithy/build/generated/spring/files-module/`

con paquetes del estilo `com.smithy.g.files.server.files.*` (controllers, modelos OpenAPI, invoker, interfaces delegadas).

---

## Pasos

### 1. Generar artefactos desde Smithy

Desde la carpeta `smithy/`:

```powershell
.\gradlew.bat smithyBuild --no-daemon
.\gradlew.bat generateFilesJavaServer --no-daemon
```

En Unix:

```bash
./gradlew smithyBuild --no-daemon
./gradlew generateFilesJavaServer --no-daemon
```

(O usar `generateAllJavaServers` / `generateAllCodegen` según `smithy/build.gradle`.)

Comprobar:

- OpenAPI: `smithy/build/smithyprojections/.../openapi/FilesApi.openapi.json`
- Módulo Java: `smithy/build/generated/spring/files-module/`

### 2. Diseñar la integración

Elegir una estrategia y documentarla en el PR:

- **Opción A:** Repo **multimódulo Maven**: módulo core (JPA, servicios, seguridad) + módulo generado o submódulo con el `pom.xml` emitido bajo `files-module`.
- **Opción B:** Incorporar el código generado bajo `src/main/java` con paquetes claros y ajustar dependencias en `pom.xml` (evitar dos clases `Application` / dos `@SpringBootApplication` sin resolver).

### 3. Delegate pattern

El generador Spring suele exponer **interfaces delegadas**. Implementar cada delegado llamando a `com.githubx.githubfilesms.service.*` (y repositorios), o mover la lógica manteniendo transacciones y reglas de negocio.

### 4. Qué borrar (solo cuando arranque y tests pasen)

**Eliminar** lo sustituido por la API generada (p. ej. `FileController`, `CommitController`, DTOs duplicados si el modelo OpenAPI los cubre).

**No borrar** sin revisión:

- `SecurityConfig`, `JwtAuthConverter`, filtros OAuth2
- Entidades JPA, `dao`, MapStruct, `GlobalExceptionHandler` / manejo de errores
- `application.yaml`, `application-dev.yaml`, `data.sql`, etc.

Integrar lo que el generador no provee con el nuevo arranque o ajustar `@ComponentScan` / exclusión de duplicados.

### 5. Consistencia

- `server.servlet.context-path` y Springdoc: evitar **dos** definiciones OpenAPI conflictivas.
- Proteger los endpoints generados con la misma política JWT que la app actual.

### 6. Validación

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Probar Swagger y al menos un flujo GET sobre `/v1/repos/...` equivalente al comportamiento anterior.

### 7. Documentación del cambio

En commit/PR: comandos Gradle usados, estructura de módulos, lista de clases/paquetes manuales eliminados y motivo.

---

## Restricciones

- No eliminar la carpeta `smithy/` ni el flujo de generación; debe seguir siendo posible **regenerar** tras cambiar el modelo.
- Si el modelo Smithy **no cubre** toda la superficie del código manual (commits, rutas, etc.), **actualizar primero** los `.smithy`, regenerar OpenAPI y código, y solo entonces retirar la lógica manual que ya tenga equivalente en el contrato.

---

## Resumen en una línea

> Integrar `smithy/build/generated/spring/files-module` en el Maven principal con delegate pattern, conectar con `com.githubx.githubfilesms.service`, y borrar los controladores manuales cuando `./mvnw test` y el arranque local sean correctos.
