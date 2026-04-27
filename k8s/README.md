# Kubernetes manifests (base)

Estos manifiestos son una base para despliegue en Kubernetes alineada a `docs/ARCHITECTURE.md`:

- Configuracion externa por variables de entorno.
- Secretos fuera del codigo versionado.

## Archivos

- `configmap.yaml`: variables no sensibles (`DB_HOST`, `DB_PORT`, `DB_NAME`, `JWT_ISSUER_URI`, `SERVER_PORT`).
- `secret.example.yaml`: ejemplo de secretos (NO usar en produccion tal cual).
- `deployment.yaml`: despliegue de la app con probes y recursos.
- `service.yaml`: servicio interno `ClusterIP`.

## Uso

1. Ajusta la imagen en `deployment.yaml`:

```yaml
image: ghcr.io/your-org/github-files-ms:latest
```

2. Crea el secreto real (sin commitear credenciales):

```bash
kubectl create secret generic github-files-ms-secret \
  --from-literal=DB_USERNAME='<usuario>' \
  --from-literal=DB_PASSWORD='<password>' \
  -n <namespace>
```

3. Aplica manifiestos:

```bash
kubectl apply -f k8s/configmap.yaml -n <namespace>
kubectl apply -f k8s/deployment.yaml -n <namespace>
kubectl apply -f k8s/service.yaml -n <namespace>
```

4. Verifica estado:

```bash
kubectl get pods -l app=github-files-ms -n <namespace>
kubectl get svc github-files-ms -n <namespace>
kubectl logs deploy/github-files-ms -n <namespace>
```
