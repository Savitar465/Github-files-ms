package com.githubx.githubfilesms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Value("${server.port:8081}")
    private int serverPort;

    /**
     * Si se define (p. ej. {@code https://staging.ejemplo.com/api}), sustituye el servidor "Local" en Swagger.
     * Por defecto vacio: se usa una URL relativa al context-path para que Swagger UI use el mismo host/puerto
     * que el navegador (port-forward, otro localhost, Ingress, etc.).
     */
    @Value("${openapi.server-local-url:}")
    private String openApiServerLocalUrl;

    @Bean
    @Primary
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        String localServerUrl = (openApiServerLocalUrl != null && !openApiServerLocalUrl.isBlank())
                ? openApiServerLocalUrl
                : contextPath;

        return new OpenAPI()
                .info(new Info()
                        .title("Github Files API")
                        .version("1.0.0")
                        .description("API REST para gestion de archivos, commits y contenido de repositorios estilo GitHub")
                        .contact(new Contact()
                                .name("Github Files Team")
                                .email("support@github-files.local"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url(localServerUrl)
                                .description("Local (mismo host/puerto que el navegador; recomendado con port-forward)"),
                        new Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Directo al puerto del proceso Spring (solo si coincide con como abres la UI)"),
                        new Server().url("https://api.github-files.local" + contextPath).description("Production")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtenido del servidor de autenticacion")));
    }
}
