package dev.jarand.authapi.grantedtype.repository;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class GrantedTypeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GrantedTypeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(GrantedType grantedType) {
        jdbcTemplate.update("INSERT INTO granted_type (grant_type, client_id) VALUES (:grant_type, :client_id)",
                new MapSqlParameterSource()
                        .addValue("grant_type", grantedType.getGrantType())
                        .addValue("client_id", grantedType.getClientId()));
    }

    public List<GrantedType> get(String clientId) {
        return jdbcTemplate.query("SELECT grant_type, client_id FROM granted_type WHERE client_id = :client_id",
                new MapSqlParameterSource().addValue("client_id", clientId),
                this::mapRow);
    }

    public Optional<GrantedType> get(String grantType, String clientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT grant_type, client_id FROM granted_type WHERE grant_type = :grant_type AND client_id = :client_id",
                    new MapSqlParameterSource()
                            .addValue("grant_type", grantType)
                            .addValue("client_id", clientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    private GrantedType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GrantedType(resultSet.getString("grant_type"), resultSet.getString("client_id"));
    }
}
