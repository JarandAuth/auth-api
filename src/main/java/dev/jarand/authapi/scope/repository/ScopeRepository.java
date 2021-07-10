package dev.jarand.authapi.scope.repository;

import dev.jarand.authapi.scope.domain.Scope;
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
public class ScopeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScopeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Scope scope) {
        jdbcTemplate.update("INSERT INTO scope (id, description, time_of_creation) VALUES (:id, :description, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("id", scope.getId())
                        .addValue("description", scope.getDescription())
                        .addValue("time_of_creation", scope.getTimeOfCreation().toString()));
    }

    public List<Scope> getScopes() {
        return jdbcTemplate.query("SELECT id, description, time_of_creation FROM scope", this::mapRow);
    }

    public List<Scope> getScopes(List<String> scopeIds) {
        return jdbcTemplate.query(
                "SELECT id, description, time_of_creation FROM scope WHERE id IN (:scopeIds)",
                new MapSqlParameterSource("scopeIds", scopeIds),
                this::mapRow);
    }

    public Optional<Scope> getScope(String id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, description, time_of_creation FROM scope WHERE id = :id",
                    new MapSqlParameterSource("id", id),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    private Scope mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Scope(resultSet.getString("id"), resultSet.getString("description"), Instant.parse(resultSet.getString("time_of_creation")));
    }
}
