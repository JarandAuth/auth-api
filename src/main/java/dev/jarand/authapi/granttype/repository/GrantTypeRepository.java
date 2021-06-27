package dev.jarand.authapi.granttype.repository;

import dev.jarand.authapi.granttype.domain.GrantType;
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
public class GrantTypeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GrantTypeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(GrantType grantType) {
        jdbcTemplate.update("INSERT INTO grant_type (grant_type, time_of_creation) VALUES (:grant_type, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("grant_type", grantType.getGrantType())
                        .addValue("time_of_creation", grantType.getTimeOfCreation().toString()));
    }

    public List<GrantType> get() {
        return jdbcTemplate.query("SELECT grant_type, time_of_creation FROM grant_type", this::mapRow);
    }

    public Optional<GrantType> get(String grantType) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT grant_type, time_of_creation FROM grant_type WHERE grant_type = :grant_type",
                    new MapSqlParameterSource().addValue("grant_type", grantType),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    private GrantType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GrantType(resultSet.getString("grant_type"), Instant.parse(resultSet.getString("time_of_creation")));
    }
}
