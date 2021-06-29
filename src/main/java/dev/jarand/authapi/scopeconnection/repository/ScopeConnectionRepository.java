package dev.jarand.authapi.scopeconnection.repository;

import dev.jarand.authapi.scopeconnection.domain.ScopeConnection;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class ScopeConnectionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeConnectionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ScopeConnection scopeConnection) {
        jdbcTemplate.update("INSERT INTO scope_connection (scope_id, client_id, time_of_creation) VALUES (:scope_id, :client_id, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("scope_id", scopeConnection.getScopeId())
                        .addValue("client_id", scopeConnection.getClientId())
                        .addValue("time_of_creation", scopeConnection.getTimeOfCreation().toString()));
    }

    public Optional<ScopeConnection> get(String scopeId, String clientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT scope_id, client_id, time_of_creation FROM scope_connection WHERE scope_id = :scope_id AND client_id = :client_id",
                    new MapSqlParameterSource().addValue("scope_id", scopeId).addValue("client_id", clientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<ScopeConnection> get() {
        return jdbcTemplate.query("SELECT scope_id, client_id, time_of_creation FROM scope_connection", this::mapRow);
    }

    public List<ScopeConnection> getByScopeId(String scopeId) {
        return jdbcTemplate.query(
                "SELECT scope_id, client_id, time_of_creation FROM scope_connection WHERE scope_id = :scope_id",
                new MapSqlParameterSource().addValue("scope_id", scopeId),
                this::mapRow);
    }

    public List<ScopeConnection> getByClientId(String clientId) {
        return jdbcTemplate.query(
                "SELECT scope_id, client_id, time_of_creation FROM scope_connection WHERE client_id = :client_id",
                new MapSqlParameterSource().addValue("client_id", clientId),
                this::mapRow);
    }

    private ScopeConnection mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new ScopeConnection(resultSet.getString("scope_id"), resultSet.getString("client_id"), Instant.parse(resultSet.getString("time_of_creation")));
    }
}
