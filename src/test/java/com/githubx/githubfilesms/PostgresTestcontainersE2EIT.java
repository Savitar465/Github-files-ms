package com.githubx.githubfilesms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * E2E opcional: contexto Spring completo contra PostgreSQL real (Docker).
 * Si Docker no está disponible, JUnit deshabilita la clase (no falla el build).
 */
@SpringBootTest
@ActiveProfiles("test-pg")
@Testcontainers(disabledWithoutDocker = true)
class PostgresTestcontainersE2EIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Test
    void contextLoads() {
    }
}
