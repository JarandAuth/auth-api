package dev.jarand.authapi.scope.repository;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScopeConnectionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeConnectionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ScopeConnection scopeConnection) {
        jdbcTemplate.update("INSERT INTO scope_connection (scope_id, jarand_client_id) VALUES (:scope_id, :jarand_client_id)",
                new MapSqlParameterSource()
                        .addValue("scope_id", scopeConnection.getScopeId())
                        .addValue("jarand_client_id", scopeConnection.getJarandClientId()));
    }
}
