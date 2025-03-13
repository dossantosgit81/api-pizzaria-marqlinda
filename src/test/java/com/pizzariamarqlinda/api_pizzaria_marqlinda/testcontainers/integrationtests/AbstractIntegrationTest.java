package com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:16.8");

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment env = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
            env.getPropertySources().addFirst(testContainers);
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                "spring.datasource.url", postgresql.getJdbcUrl(),
                "spring.datasource.username", postgresql.getUsername(),
                "spring.datasource.password", postgresql.getPassword()
            );
        }

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgresql)).join();

        }
    }
}
