# Kubernetes manifests (base)

Estos manifiestos son una base para despliegue en Kubernetes alineada a `docs/ARCHITECTURE.md`:

- Configuracion externa por variables de entorno.
- Secretos fuera del codigo versionado.

## Archivos

- `configmap.yaml`: variables no sensibles (`DB_HOST`, `DB_PORT`, `DB_NAME`, `SERVER_PORT`) y flags para Arranque local en cluster (`APP_SECURITY_OAUTH2_ENABLED=false`, DDL `update`; **no usar asi en prod** sin IdP real).
- `secret.example.yaml`: ejemplo de secretos (NO usar en produccion tal cual).
- `postgres.yaml`: PostgreSQL base para entorno local/dev (PVC + Deployment + Service).
- `deployment.yaml`: despliegue para entorno local (`image: github-files-ms:local`, 1 replica).
- `deployment.prod.yaml`: despliegue para prod/CI (`image` en registry, 2 replicas).
- `service.yaml`: servicio interno `ClusterIP`.

## Uso

1. Construye la imagen local:

```bash
docker build -t github-files-ms:local .
```

2. Crea el secreto real (sin commitear credenciales):

```bash
kubectl create secret generic github-files-ms-secret \
  --from-literal=DB_USERNAME='<usuario>' \
  --from-literal=DB_PASSWORD='<password>' \
  -n <namespace>
```

3. Levanta PostgreSQL en el cluster:

```bash
kubectl apply -f k8s/postgres.yaml -n <namespace>
```

4. Aplica manifiestos de la aplicacion (local):

```bash
kubectl apply -f k8s/configmap.yaml -n <namespace>
kubectl apply -f k8s/deployment.yaml -n <namespace>
kubectl apply -f k8s/service.yaml -n <namespace>
```

Para prod/CI, aplica `deployment.prod.yaml` en lugar de `deployment.yaml` y reemplaza la imagen por el tag publicado en registry:

```bash
kubectl apply -f k8s/deployment.prod.yaml -n <namespace>
```

5. Verifica estado:

```bash
kubectl get pods -l app=postgres-github-files-ms -n <namespace>
kubectl get pods -l app=github-files-ms -n <namespace>
kubectl get svc postgres github-files-ms -n <namespace>
kubectl logs deploy/postgres-github-files-ms -n <namespace>
kubectl get svc github-files-ms -n <namespace>
kubectl logs deploy/github-files-ms -n <namespace>
```

6. Prueba endpoint de salud (port-forward):

```bash
kubectl port-forward svc/github-files-ms 8080:80 -n <namespace>
curl http://localhost:8080/api/actuator/health
```

---

## Docker Desktop / kubectl

- Activa Kubernetes en Docker Desktop y espera **Kubernetes is running**.
- Usa contexto local: `kubectl config use-context docker-desktop`.
- Si `kubectl` muestra **Authentication required** o no hay contextos, ejecuta `unset KUBECONFIG` y revisa `~/.kube/config`.

---

## Por que puede fallar el pod (CrashLoopBackOff)

Revisa siempre los logs primero:

```bash
kubectl logs deploy/github-files-ms -n <namespace> --tail=100
```

Causas frecuentes en **smoke test local**:

1. **Imagen inexistente** → `ErrImagePull` / `ImagePullBackOff`  
   - Construye y etiqueta: `docker build -t github-files-ms:local .`  
   - En Docker Desktop normalmente la imagen local esta disponible para el cluster (`imagePullPolicy: IfNotPresent`).

2. **Base de datos vacia + `ddl-auto: validate`** (valor por defecto en `application.yaml`)  
   - Hibernate no crea tablas y el arranque falla.  
   - El `configmap.yaml` de esta carpeta fuerza **`SPRING_JPA_HIBERNATE_DDL_AUTO=update`** solo para pruebas en cluster. En produccion usa migraciones (Flyway/Liquibase) y `validate`.

3. **OAuth2 / JWT con issuer inalcanzable**  
   - Si defines `JWT_ISSUER_URI` hacia un host que no existe o no es un IdP valido, Spring puede fallar al inicializar el cliente OAuth2.  
   - El `configmap.yaml` actual desactiva OAuth para **solo laboratorio local** (`APP_SECURITY_OAUTH2_ENABLED=false` + exclusion del auto-config). **No copies eso a produccion.**

4. **Rollout colgado / dos ReplicaSets**  
   - Tras cambiar imagen o ConfigMap: `kubectl rollout restart deployment/github-files-ms -n <namespace>`.  
   - Si un pod queda en `Terminating`: `kubectl delete pod <nombre> -n <namespace> --force --grace-period=0` (solo si lleva mucho tiempo colgado).

---

## Produccion (checklist breve)

- Imagen desde registry versionada (usa `deployment.prod.yaml` y sustituye la imagen).
- **No** uses `SPRING_JPA_HIBERNATE_DDL_AUTO=update` sin estrategia de migraciones.
- Activa OAuth de verdad: `APP_SECURITY_OAUTH2_ENABLED=true` (o elimina la variable para usar el default), quita `SPRING_AUTOCONFIGURE_EXCLUDE`, configura `JWT_ISSUER_URI` (u otro mecanismo OAuth2 que uses) hacia tu IdP real.
- Credenciales solo en `Secret`, no en ConfigMap ni en el repo.
