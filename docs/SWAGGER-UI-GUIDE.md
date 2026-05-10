# Guía de Pruebas - Swagger UI y Postman

**Swagger UI:** http://localhost:8081/api/swagger-ui.html

---

## Configuración de Postman

### 1. Crear una Collection

1. Abrir Postman
2. Click en `Collections` (panel izquierdo)
3. Click en `+` o `New Collection`
4. Nombre: `Github Files MS`
5. Click `Create`

### 2. Configurar Variables de Collection

1. Click derecho en la collection `Github Files MS`
2. Seleccionar `Edit`
3. Ir a tab `Variables`
4. Agregar las siguientes variables:

| Variable | Initial Value | Current Value |
|----------|---------------|---------------|
| `base_url` | `http://localhost:8081/api` | `http://localhost:8081/api` |
| `owner` | `demo-user` | `demo-user` |
| `repo` | `demo-repo` | `demo-repo` |
| `branch` | `main` | `main` |

5. Click `Save`

### 3. Crear Requests

Para cada request nueva:
1. Click derecho en la collection
2. `Add request`
3. Dar nombre descriptivo
4. Usar `{{base_url}}` en lugar de `http://localhost:8081/api`

**Ejemplo de URL con variables:**
```
{{base_url}}/v1/repos/{{owner}}/{{repo}}/contents
```

### 4. Headers por Defecto (opcional)

Si quieres configurar headers para toda la collection:
1. Edit collection → tab `Headers`
2. Agregar:
   - `Content-Type`: `application/json`
   - `Accept`: `application/json`

---

## Datos de Demo Disponibles

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| branch | `main` |
| commit sha | `abc123def456789012345678901234567890abcd` |
| archivo | `README.md` (sha: `a1b2c3d4e5f6789012345678901234567890abcd`) |
| carpeta | `src` |
| archivo en carpeta | `src/main.java` |

---

## Endpoints Disponibles en Swagger UI

> **Nota:** Los endpoints con greedy path (`{filePath+}`) no aparecen en Swagger UI.
> Para esos casos usar curl o el query param `path`.

---

## 1. Listar contenido del repositorio (raíz)

**Endpoint:** `GET /v1/repos/{owner}/{repo}/contents`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| path | *(vacío)* |
| ref | *(vacío)* |

**Pasos:**
1. Busca el endpoint en Swagger UI
2. Click **"Try it out"**
3. Llena los campos según la tabla
4. Click **"Execute"**

**Respuesta esperada:** Lista con `README.md` y `src`

---

## 2. Ver contenido de carpeta "src"

**Endpoint:** `GET /v1/repos/{owner}/{repo}/contents`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| path | `src` |
| ref | *(vacío)* |

**Respuesta esperada:** Lista con `main.java`

---

## 3. Ver contenido de un archivo

**Endpoint:** `GET /v1/repos/{owner}/{repo}/contents`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| path | `README.md` |
| ref | *(vacío)* |

**Respuesta esperada:** Objeto `file` con contenido en base64

Para ver `src/main.java`:

| Campo | Valor |
|-------|-------|
| path | `src/main.java` |

---

## 4. Listar commits

**Endpoint:** `GET /v1/repos/{owner}/{repo}/commits`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| sha | *(vacío)* |
| path | *(vacío)* |
| page | *(vacío)* |
| perPage | *(vacío)* |

**Respuesta esperada:** Lista con 1 commit "Initial commit"

---

## 5. Ver detalle de un commit

**Endpoint:** `GET /v1/repos/{owner}/{repo}/commits/{sha}`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| sha | `abc123def456789012345678901234567890abcd` |

**Respuesta esperada:** Detalle del commit con archivos modificados

---

## 6. Ver diff de un commit

**Endpoint:** `GET /v1/repos/{owner}/{repo}/commits/{sha}/diff`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| sha | `abc123def456789012345678901234567890abcd` |

**Respuesta esperada:** Diff en formato texto

---

## 7. Crear una carpeta

**Endpoint:** `POST /v1/repos/{owner}/{repo}/folders`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |

**Request body:**
```json
{
  "path": "mi-carpeta",
  "message": "Crear carpeta nueva",
  "branch": "main"
}
```

**Respuesta esperada:** 201 Created con datos de la carpeta

---

## 8. Descargar archivo raw

**Endpoint:** `GET /v1/repos/{owner}/{repo}/download`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| path | `README.md` |
| ref | *(vacío)* |

**Respuesta esperada:** Contenido binario del archivo

---

## 9. Comparar branches

**Endpoint:** `GET /v1/repos/{owner}/{repo}/compare/{baseBranch}/{headBranch}`

| Campo | Valor |
|-------|-------|
| owner | `demo-user` |
| repo | `demo-repo` |
| baseBranch | `main` |
| headBranch | `develop` |

**Nota:** Requiere que exista el branch `develop` con commits

---

## Endpoints NO disponibles en Swagger UI (usar Postman)

Los siguientes endpoints usan greedy path (`{filePath+}`) y no aparecen en Swagger UI.
Esto es una limitación del generador OpenAPI, no del modelo Smithy.

> **Tip:** Si configuraste las variables de collection, puedes usar:
> `{{base_url}}/v1/repos/{{owner}}/{{repo}}/contents/archivo.txt`
> en lugar de la URL completa.

---

### 10. Crear archivo (PUT)

**Postman:**

| Campo | Valor |
|-------|-------|
| Method | `PUT` |
| URL | `http://localhost:8081/api/v1/repos/demo-user/demo-repo/contents/nuevo-archivo.txt` |
| Headers | `Content-Type: application/json` |

**Body (raw JSON):**
```json
{
  "message": "Crear archivo de prueba",
  "content": "SG9sYSBtdW5kbyE=",
  "branch": "main"
}
```

**Pasos en Postman:**
1. Crear nueva request
2. Seleccionar método `PUT`
3. Pegar la URL
4. Ir a tab `Headers`, agregar `Content-Type` = `application/json`
5. Ir a tab `Body`, seleccionar `raw` y `JSON`
6. Pegar el JSON del body
7. Click `Send`

**Respuesta esperada:** 201 Created

---

### 11. Ver archivo por path (GET)

**Postman:**

| Campo | Valor |
|-------|-------|
| Method | `GET` |
| URL | `http://localhost:8081/api/v1/repos/demo-user/demo-repo/contents/README.md` |

Para archivos en subcarpetas:
| URL | `http://localhost:8081/api/v1/repos/demo-user/demo-repo/contents/src/main.java` |

**Pasos en Postman:**
1. Crear nueva request
2. Seleccionar método `GET`
3. Pegar la URL
4. Click `Send`

**Respuesta esperada:** 200 OK con contenido del archivo

---

### 12. Actualizar archivo (PATCH)

**Postman:**

| Campo | Valor |
|-------|-------|
| Method | `PATCH` |
| URL | `http://localhost:8081/api/v1/repos/demo-user/demo-repo/contents/README.md` |
| Headers | `Content-Type: application/json` |

**Body (raw JSON):**
```json
{
  "message": "Actualizar README",
  "content": "TnVldm8gY29udGVuaWRvIGRlbCBSRUFETUU=",
  "sha": "a1b2c3d4e5f6789012345678901234567890abcd",
  "branch": "main"
}
```

**Nota:** El `sha` debe ser el SHA actual del archivo (lo obtienes del GET anterior)

**Respuesta esperada:** 200 OK con nuevo SHA

---

### 13. Eliminar archivo (DELETE)

**Postman:**

| Campo | Valor |
|-------|-------|
| Method | `DELETE` |
| URL | `http://localhost:8081/api/v1/repos/demo-user/demo-repo/contents/nuevo-archivo.txt` |

**Query Params (tab Params en Postman):**

| Key | Value |
|-----|-------|
| sha | `SHA_DEL_ARCHIVO` |
| message | `Eliminar archivo de prueba` |
| branch | `main` |

**Pasos en Postman:**
1. Crear nueva request
2. Seleccionar método `DELETE`
3. Pegar la URL base (sin query params)
4. Ir a tab `Params`
5. Agregar los 3 parámetros de la tabla
6. Click `Send`

**Respuesta esperada:** 200 OK

---

## Referencia Base64

| Texto | Base64 |
|-------|--------|
| Hola mundo! | `SG9sYSBtdW5kbyE=` |
| Nuevo contenido del README | `TnVldm8gY29udGVuaWRvIGRlbCBSRUFETUU=` |
| Hello World! | `SGVsbG8gV29ybGQh` |
| Test content | `VGVzdCBjb250ZW50` |

**Convertir texto a base64 (bash):**
```bash
echo -n "tu texto aqui" | base64
```

**Convertir base64 a texto (bash):**
```bash
echo "dHUgdGV4dG8gYXF1aQ==" | base64 -d
```
