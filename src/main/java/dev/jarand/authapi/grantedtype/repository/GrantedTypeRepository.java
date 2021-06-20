package dev.jarand.authapi.grantedtype.repository;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GrantedTypeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GrantedTypeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(GrantedType grantedType) {
        jdbcTemplate.update("INSERT INTO granted_type (grant_type, jarand_client_id) VALUES (:grant_type, :jarand_client_id)",
                new MapSqlParameterSource()
                        .addValue("grant_type", grantedType.getGrantType())
                        .addValue("jarand_client_id", grantedType.getJarandClientId()));
    }

    public Optional<GrantedType> get(String grantType, UUID jarandClientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT grant_type, jarand_client_id FROM granted_type WHERE grant_type = :grant_type AND jarand_client_id = :jarand_client_id",
                    new MapSqlParameterSource()
                            .addValue("grant_type", grantType)
                            .addValue("jarand_client_id", jarandClientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    private GrantedType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new GrantedType(resultSet.getString("grant_type"), UUID.fromString(resultSet.getString("jarand_client_id")));
    }
}
