# Github Files MS

Microservicio base con Spring Boot + gRPC y un modulo `smithy/` para definir el contrato de API y generar artefactos (OpenAPI, cliente TypeScript y servidor Spring).

## Checklist rapido

- [ ] Instalar prerequisitos (Java, Maven/Gradle wrappers, Node para cliente TS).
- [ ] Levantar la app principal Spring Boot.
- [ ] Validar el contrato Smithy.
- [ ] Generar OpenAPI y codigo derivado.
- [ ] Integrar o ejecutar el codigo generado segun el caso de uso.

## Estructura del proyecto

```text
.
├── src/                          # App principal Spring Boot
├── smithy/                       # Contrato Smithy + codegen
│   ├── model/common/common.smithy
│   └── model/files/
│       ├── services/files-api.smithy
│       ├── operations/*.smithy
│       └── structures/*.smithy
├── docs/
├── pom.xml
├── mvnw / mvnw.cmd
└── README.md
```

## Prerequisitos

- Java (el `pom.xml` declara `java.version=25`)
- Maven Wrapper (`mvnw.cmd`, ya incluido en el repo)
- Gradle Wrapper para Smithy (`smithy/gradlew.bat`, ya incluido)
- Node.js + npm (solo si vas a usar/publicar el cliente TypeScript generado)

## Ejecutar la app principal

Desde la raiz del repo:

```powershell
Set-Location "C:\Projects\Github-files-ms"
.\mvnw.cmd clean spring-boot:run
```

Ejecutar pruebas:

```powershell
Set-Location "C:\Projects\Github-files-ms"
.\mvnw.cmd test
```

## Flujo Smithy (contrato -> OpenAPI -> codigo)

### 1) Validar el modelo Smithy

```powershell
Set-Location "C:\Projects\Github-files-ms\smithy"
.\gradlew.bat smithyBuild --no-daemon
```

### 2) Generar OpenAPI

El mismo `smithyBuild` genera la especificacion OpenAPI del servicio `FilesApi`.

Ruta esperada:

- `smithy/build/smithyprojections/<project>/github-files/openapi/FilesApi.openapi.json`

### 3) Generar codigo derivado

```powershell
Set-Location "C:\Projects\Github-files-ms\smithy"
.\gradlew.bat generateAllCodegen --no-daemon
```

Tambien puedes generar por tipo:

```powershell
Set-Location "C:\Projects\Github-files-ms\smithy"
.\gradlew.bat generateFilesTypeScriptClient --no-daemon
.\gradlew.bat generateFilesJavaServer --no-daemon
```

Salidas esperadas:

- Cliente TS: `smithy/build/generated/typescript/files-client`
- Server Spring (stubs): `smithy/build/generated/spring/files-module`

## Como usar el codigo generado con Smithy

### A) Usar OpenAPI como contrato unico

1. Genera o actualiza el archivo OpenAPI con `smithyBuild`.
2. Publica/versiona `FilesApi.openapi.json` para consumidores.
3. Usa ese JSON en gateways, portales de API o validacion de compatibilidad.

### B) Usar el cliente TypeScript generado

1. Genera el cliente con `generateFilesTypeScriptClient`.
2. En tu frontend o BFF, instala desde carpeta local.

```powershell
Set-Location "C:\Projects\Github-files-ms\smithy\build\generated\typescript\files-client"
npm install
npm run build
```

3. Consumelo desde otro proyecto (ejemplo local):

```powershell
npm install "C:\Projects\Github-files-ms\smithy\build\generated\typescript\files-client"
```

4. Importa el cliente y configura base URL + auth bearer en tu codigo consumidor.

### C) Usar el server Spring generado (stubs)

1. Genera el modulo con `generateFilesJavaServer`.
2. Compilalo o ejecutalo de forma aislada para revisar endpoints generados.

```powershell
Set-Location "C:\Projects\Github-files-ms\smithy\build\generated\spring\files-module"
# Si el modulo trae wrapper Maven:
.\mvnw.cmd test
.\mvnw.cmd install
.\mvnw.cmd spring-boot:run //(optional)
# Alternativa si no trae wrapper:
mvn test
mvn install //(optional)
mvn spring-boot:run
```

3. Implementa la logica en delegates/services del modulo generado, o usa el codigo como base para mover interfaces/modelos al microservicio principal.

## Recomendacion de trabajo

- Mantener `smithy/model/files` como fuente de verdad del contrato.
- Regenerar OpenAPI y stubs despues de cambios de contrato.
- Evitar editar manualmente codigo generado si no hay estrategia de regeneracion controlada.
- Si se personaliza el codigo generado, documentar que partes son "manuales" para no perderlas en la siguiente generacion.

## Referencias internas

- Guia de Smithy: `smithy/README.md`
- Arquitectura general: `docs/ARCHITECTURE.md`
- Reglas de codigo: `docs/CODE-RULES.md`
- Contribucion: `docs/CONTRIBUTING.md`


