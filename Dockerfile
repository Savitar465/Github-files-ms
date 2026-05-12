# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jre
LABEL org.opencontainers.image.title="Github-files-ms" \
  org.opencontainers.image.description="Microservicio de gestion de archivos y contenido" \
  org.opencontainers.image.vendor="Githubx" \
  org.opencontainers.image.url="https://github.com/Savitar465/Github-files-ms" \
  org.opencontainers.image.source="https://github.com/Savitar465/Github-files-ms" \
  org.opencontainers.image.documentation="https://github.com/Savitar465/Github-files-ms/blob/main/README.md" \
  org.opencontainers.image.authors="Savitar465"

WORKDIR /app

# Download AWS RDS SSL certificate
RUN apt-get update && apt-get install -y --no-install-recommends curl \
  && curl -o /app/global-bundle.pem https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem \
  && apt-get purge -y curl && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

RUN groupadd -r spring && useradd -r -g spring spring

# Copy pre-built JAR from CI
COPY target/*.jar app.jar

RUN chown spring:spring /app/global-bundle.pem

USER spring:spring

# Puerto interno 8080 (K8s/Docker). En local, el default de application.yaml es 8081.
ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
