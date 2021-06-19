package dev.jarand.authapi.scope.repository;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<ScopeConnection> get(String scopeId, UUID jarandClientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT scope_id, jarand_client_id FROM scope_connection WHERE scope_id = :scope_id AND jarand_client_id = :jarand_client_id",
                    new MapSqlParameterSource().addValue("scope_id", scopeId).addValue("jarand_client_id", jarandClientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    private ScopeConnection mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ScopeConnection(resultSet.getString("scope_id"), UUID.fromString(resultSet.getString("jarand_client_id")));
    }
}
