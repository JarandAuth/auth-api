package dev.jarand.authapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Instant;
import java.util.function.Supplier;

@TestConfiguration
public class ApiTestConfig {

    @Bean
    @Primary
    public Supplier<Instant> instantSupplierTest() {
        return () -> Instant.parse("2021-01-01T12:05:10Z");
    }
}
