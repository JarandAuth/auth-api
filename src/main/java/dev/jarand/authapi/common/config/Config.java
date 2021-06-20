package dev.jarand.authapi.common.config;

import io.jsonwebtoken.Clock;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class Config {

    @Bean
    public Supplier<UUID> uuidSupplier() {
        return UUID::randomUUID;
    }

    @Bean
    public Supplier<Instant> instantSupplier() {
        return Instant::now;
    }

    @Bean
    public Clock clock() {
        return new DefaultClock();
    }
}
