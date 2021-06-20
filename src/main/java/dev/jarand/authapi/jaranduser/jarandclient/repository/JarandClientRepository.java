package dev.jarand.authapi.jaranduser.jarandclient.repository;

import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JarandClientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JarandClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<JarandClient> getClient(String clientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT client_id, client_secret, owner_id, time_of_creation FROM jarand_client WHERE client_id = :client_id",
                    new MapSqlParameterSource("client_id", clientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<JarandClient> getClients(UUID ownerId) {
        return jdbcTemplate.query(
                "SELECT client_id, client_secret, owner_id, time_of_creation FROM jarand_client WHERE owner_id = :owner_id",
                new MapSqlParameterSource("owner_id", ownerId),
                this::mapRow);
    }

    public void createClient(JarandClient client) {
        jdbcTemplate.update("INSERT INTO jarand_client(client_id, client_secret, owner_id, time_of_creation) " +
                        "VALUES (:client_id, :client_secret, :owner_id, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("client_id", client.getClientId())
                        .addValue("client_secret", client.getClientSecret())
                        .addValue("owner_id", client.getOwnerId())
                        .addValue("time_of_creation", client.getTimeOfCreation().toString()));
    }

    private JarandClient mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new JarandClient(
                resultSet.getString("client_id"),
                resultSet.getString("client_secret"),
                UUID.fromString(resultSet.getString("owner_id")),
                Instant.parse(resultSet.getString("time_of_creation")));
    }
}
