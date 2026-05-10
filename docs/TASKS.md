# Tareas de Implementacion - Github-files-ms

> Checklist de implementacion siguiendo la arquitectura definida en `ARCHITECTURE.md` y `CODE-RULES.md`.
> Base de datos: **PostgreSQL**

---

## Fase 0: Configuracion Inicial

- [x] Agregar dependencias faltantes en `pom.xml` (PostgreSQL, MapStruct, Validation)
- [x] Configurar `application.yaml` con datasource PostgreSQL
- [x] Crear estructura de paquetes base

---

## Fase 1: Infraestructura Base

### 1.1 Configuracion
- [x] `config/OpenApiConfig.java` - Configuracion springdoc
- [x] `config/security/SecurityConfig.java` - OAuth2 Resource Server + JWT
- [x] `config/security/JwtAuthConverter.java` - Converter de claims a authorities

### 1.2 Manejo de Errores
- [x] `util/errorhandling/GlobalExceptionHandler.java` - @ControllerAdvice
- [x] `util/errorhandling/BusinessException.java` - Excepcion base
- [x] `util/errorhandling/EntityNotFoundException.java` - 404
- [x] `util/errorhandling/EntityConflictException.java` - 409
- [x] `util/errorhandling/ForbiddenException.java` - 403
- [x] `util/errorhandling/BadRequestException.java` - 400
- [x] `util/errorhandling/ErrorResponse.java` - DTO de error estandar

---

## Fase 2: Dominio de Archivos (Files)

### 2.1 DTOs
**Request:**
- [x] `dto/request/CreateFileRequest.java`
- [x] `dto/request/UpdateFileRequest.java`
- [x] `dto/request/CreateFolderRequest.java`
- [x] `dto/request/IdentityRequest.java`

**Response:**
- [x] `dto/response/FileContentResponse.java`
- [x] `dto/response/DirectoryEntryResponse.java`
- [x] `dto/response/FileOperationResponse.java`
- [x] `dto/response/CommitSummaryResponse.java`
- [x] `dto/response/CommitSignatureResponse.java`
- [x] `dto/response/DeleteFileResponse.java`
- [x] `dto/response/GetFileContentBodyResponse.java`

### 2.2 Modelo y DAO
- [x] `model/FileEntity.java` - Entidad JPA para archivos
- [x] `model/RepositoryEntity.java` - Entidad JPA para repositorios
- [x] `model/enums/GitObjectType.java` - Enum FILE/DIRECTORY
- [x] `model/enums/RepoVisibility.java` - Enum PUBLIC/PRIVATE
- [x] `dao/FileDao.java` - Repository Spring Data
- [x] `dao/RepositoryDao.java` - Repository Spring Data

### 2.3 Mapper
- [x] `mapper/FileMapper.java` - MapStruct FileEntity <-> DTOs

### 2.4 Service
- [x] `service/contratos/FileService.java` - Interface
- [x] `service/implementacion/FileServiceImpl.java` - Implementacion

### 2.5 Controller
- [x] `controller/FileController.java` - Endpoints:
  - [x] `GET /v1/repos/{owner}/{repo}/contents/{filePath+}` - GetFileContent
  - [x] `GET /v1/repos/{owner}/{repo}/contents` - GetRepositoryContents
  - [x] `PUT /v1/repos/{owner}/{repo}/contents/{filePath+}` - CreateFile
  - [x] `PATCH /v1/repos/{owner}/{repo}/contents/{filePath+}` - UpdateFile
  - [x] `DELETE /v1/repos/{owner}/{repo}/contents/{filePath+}` - DeleteFile
  - [x] `POST /v1/repos/{owner}/{repo}/folders` - CreateFolder
  - [x] `GET /v1/repos/{owner}/{repo}/download` - GetRawFile

### 2.6 Tests
- [x] `test/controller/FileControllerTest.java`
- [x] `test/service/FileServiceTest.java`

---

## Fase 3: Dominio de Commits

### 3.1 DTOs
**Response:**
- [x] `dto/response/CommitResponse.java`
- [x] `dto/response/CommitSignatureResponse.java` (ya existia)
- [x] `dto/response/CommitParentResponse.java`
- [x] `dto/response/CommitFileResponse.java`
- [x] `dto/response/CompareResponse.java`
- [x] `dto/response/ListCommitsResponse.java`
- [x] `dto/response/GetCommitResponse.java`
- [x] `dto/response/PaginationMeta.java`

### 3.2 Modelo y DAO
- [x] `model/CommitEntity.java` - Entidad JPA para commits
- [x] `model/CommitFileEntity.java` - Archivos modificados en commit
- [x] `dao/CommitDao.java` - Repository Spring Data

### 3.3 Mapper
- [x] `mapper/CommitMapper.java` - MapStruct CommitEntity <-> DTOs

### 3.4 Service
- [x] `service/contratos/CommitService.java` - Interface
- [x] `service/implementacion/CommitServiceImpl.java` - Implementacion

### 3.5 Controller
- [x] `controller/CommitController.java` - Endpoints:
  - [x] `GET /v1/repos/{owner}/{repo}/commits` - ListCommits
  - [x] `GET /v1/repos/{owner}/{repo}/commits/{sha}` - GetCommit
  - [x] `GET /v1/repos/{owner}/{repo}/commits/{sha}/diff` - GetCommitDiff
  - [x] `GET /v1/repos/{owner}/{repo}/compare/{base}/{head}` - CompareCommits

### 3.6 Tests
- [x] `test/controller/CommitControllerTest.java`
- [x] `test/service/CommitServiceTest.java`

---

## Fase 4: Dominio de Repositorios (soporte)

### 4.1 Modelo y DAO
- [x] `model/RepositoryEntity.java` - Entidad para repositorios (creado en Fase 2)
- [x] `model/enums/RepoVisibility.java` - Enum PUBLIC/PRIVATE (creado en Fase 2)
- [x] `dao/RepositoryDao.java` - Repository Spring Data (creado en Fase 2)

### 4.2 DTOs
- [x] `dto/response/RepositoryResponse.java`
- [x] `dto/request/CreateRepositoryRequest.java`

### 4.3 Mapper
- [x] `mapper/RepositoryMapper.java`

### 4.4 Service
- [x] `service/contratos/RepositoryService.java`
- [x] `service/implementacion/RepositoryServiceImpl.java`

---

## Fase 5: Integracion y Calidad

### 5.1 Base de Datos
- [x] Script SQL inicial (`src/main/resources/schema.sql`)
- [x] Datos de prueba (`src/main/resources/data.sql`)
- [x] Perfil de desarrollo (`application-dev.yaml`)

### 5.2 Documentacion OpenAPI
- [x] `config/OpenApiConfig.java` - Configuracion Swagger/OpenAPI
- [x] Anotaciones @Operation, @Tag en controllers
- [x] Chequeo manual Smithy <-> implementacion: mismas operaciones (Files + Commits) bajo `/v1/repos/...` (diferencia principal: ruta con `{filePath+}` en Smithy vs `/**` en Spring; ambas cubren el mismo path)

### 5.3 Tests
- [x] Tests de endpoints (controladores) con `MockMvc` (sin levantar servlet container completo)
- [x] Tests de servicio con Mockito
- [x] (Opcional) E2E con TestContainers (PostgreSQL) y Spring completo: `PostgresTestcontainersE2EIT` + perfil `test-pg` (si no hay Docker, la clase se deshabilita y no falla el build)

### 5.4 Cobertura
- [x] Cobertura minima 70% de lineas a nivel BUNDLE (JaCoCo `check` en fase `verify`)
- [x] Configurar JaCoCo en `pom.xml` (reporte en `target/site/jacoco/index.html` al ejecutar `verify`)

---

## Fase 6: Finalizacion

- [x] Revisar checklist de calidad (`ARCHITECTURE.md` seccion 11) - baseline aplicada (arquitectura por capas + DTOs)
- [x] Verificar que no hay secretos hardcodeados (credenciales via env vars; defaults solo locales/dev)
- [x] Logging estructurado configurado (patron de consola en `application.yaml`)
- [x] README actualizado con run + swagger + modulos
- [x] No hay modulos de codegen comiteados; Smithy vive en `smithy/`

---

## Progreso General

| Fase | Estado | Completado |
|------|--------|------------|
| Fase 0: Configuracion Inicial | **DONE** | 3/3 |
| Fase 1: Infraestructura Base | **DONE** | 9/9 |
| Fase 2: Dominio de Archivos | **DONE** | 25/25 |
| Fase 3: Dominio de Commits | **DONE** | 16/16 |
| Fase 4: Dominio de Repositorios | **DONE** | 8/8 |
| Fase 5: Integracion y Calidad | **DONE** | 10/10 |
| Fase 6: Finalizacion | **DONE** | 5/5 |
| **TOTAL** | **Listo** | **77/77** |

---

## Notas de Implementacion

### Base de Datos PostgreSQL
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/github_files_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

> Nota dev local: el perfil `application-dev.yaml` usa `DB_PORT` con default `5434` para evitar conflictos frecuentes con instalaciones locales de PostgreSQL en `5432`.

### Dependencias Requeridas
```xml
<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

> Ultima actualizacion: 2026-04-26
