# Github Files MS

Microservicio REST para gestion de archivos, commits y contenido de repositorios estilo GitHub.

**Stack:** Java 17 + Spring Boot 3.3.5 + PostgreSQL + MapStruct

## Requisitos

- Java 17+
- PostgreSQL 14+
- Maven (wrapper incluido)

## Inicio Rapido

### 1. Base de datos

```bash
# Crear base de datos
createdb github_files_db

# O con Docker
docker run -d --name postgres-github \
  -e POSTGRES_DB=github_files_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 postgres:14

# Si tienes conflicto local en 5432, usa 5434 en host (recomendado para dev local)
docker run -d --name postgres-github \
  -e POSTGRES_DB=github_files_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5434:5432 postgres:14
```

### 2. Ejecutar aplicacion

```powershell
# Modo desarrollo (crea tablas automaticamente)
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

# Modo produccion (requiere schema existente)
.\mvnw.cmd spring-boot:run
```

### 3. Verificar

- **API:** http://localhost:8080/api/actuator/health
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html

## Estructura del Proyecto

```
src/main/java/com/githubx/githubfilesms/
├── config/                 # Configuraciones (OpenAPI, Security)
│   └── security/          # JWT/OAuth2
├── controller/            # Endpoints REST
├── dao/                   # Repositorios Spring Data
├── dto/
│   ├── request/          # DTOs de entrada
│   └── response/         # DTOs de salida
├── mapper/               # MapStruct mappers
├── model/                # Entidades JPA
│   └── enums/
├── service/
│   ├── contratos/        # Interfaces
│   └── implementacion/   # Implementaciones
└── util/
    └── errorhandling/    # Excepciones y handlers
```

## Endpoints API

### Files
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/v1/repos/{owner}/{repo}/contents` | Listar contenido raiz |
| GET | `/v1/repos/{owner}/{repo}/contents/**` | Obtener archivo/directorio |
| PUT | `/v1/repos/{owner}/{repo}/contents/**` | Crear archivo |
| PATCH | `/v1/repos/{owner}/{repo}/contents/**` | Actualizar archivo |
| DELETE | `/v1/repos/{owner}/{repo}/contents/**` | Eliminar archivo |
| POST | `/v1/repos/{owner}/{repo}/folders` | Crear carpeta |
| GET | `/v1/repos/{owner}/{repo}/download` | Descargar archivo |

### Commits
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | `/v1/repos/{owner}/{repo}/commits` | Listar commits |
| GET | `/v1/repos/{owner}/{repo}/commits/{sha}` | Detalle de commit |
| GET | `/v1/repos/{owner}/{repo}/commits/{sha}/diff` | Diff del commit |
| GET | `/v1/repos/{owner}/{repo}/compare/{base}/{head}` | Comparar branches |

## Autenticacion

La API usa JWT Bearer tokens. Header requerido:

```
Authorization: Bearer <jwt_token>
```

Endpoints publicos (sin auth):
- `/actuator/health`
- `/actuator/info`
- `/swagger-ui/**`
- `/v3/api-docs/**`

## Variables de Entorno

| Variable | Default | Descripcion |
|----------|---------|-------------|
| `DB_HOST` | localhost | Host PostgreSQL |
| `DB_PORT` | 5432 | Puerto PostgreSQL |
| `DB_NAME` | github_files_db | Nombre de la BD |
| `DB_USERNAME` | postgres | Usuario BD |
| `DB_PASSWORD` | postgres | Password BD |
| `JWT_ISSUER_URI` | http://localhost:8080/realms/github-files | URI del emisor JWT |
| `SERVER_PORT` | 8080 | Puerto del servidor |

> Nota: en el perfil `dev` de este proyecto, el default actual para `DB_PORT` es `5434` para evitar conflictos con instalaciones locales de PostgreSQL en `5432`.

## Comandos Utiles

```powershell
# Compilar
.\mvnw.cmd clean compile

# Tests
.\mvnw.cmd test

# Empaquetar
.\mvnw.cmd clean package -DskipTests

# Ejecutar JAR
java -jar target/github-files-ms-0.0.1-SNAPSHOT.jar
```

## Modulo Smithy

El contrato de API esta definido en Smithy. Ver `smithy/README.md` para:
- Validar modelo: `.\gradlew.bat smithyBuild`
- Generar OpenAPI: `.\gradlew.bat smithyBuild`
- Generar cliente TS: `.\gradlew.bat generateFilesTypeScriptClient`

## Documentacion

- [Arquitectura](docs/ARCHITECTURE.md) - Patrones y estructura
- [Reglas de Codigo](docs/CODE-RULES.md) - Convenciones de desarrollo
- [Contribucion](docs/CONTRIBUTING.md) - Guia para contribuir
- [Tareas](docs/TASKS.md) - Checklist de implementacion
