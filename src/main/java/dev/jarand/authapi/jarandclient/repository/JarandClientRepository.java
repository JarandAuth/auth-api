package dev.jarand.authapi.jarandclient.repository;

import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jarandclient.domain.SecretClient;
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

    public List<JarandClient> getClients() {
        return jdbcTemplate.query("SELECT client_id, type, display_name, owner_id, time_of_creation, client_secret, username, password FROM jarand_client", this::mapRow);
    }

    public Optional<JarandClient> getClient(String clientId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT client_id, type, display_name, owner_id, time_of_creation, client_secret, username, password FROM jarand_client WHERE client_id = :client_id",
                    new MapSqlParameterSource("client_id", clientId),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Optional<LoginClient> getLoginClient(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT client_id, type, display_name, owner_id, time_of_creation, client_secret, username, password FROM jarand_client WHERE username = :username",
                    new MapSqlParameterSource("username", username),
                    this::mapLoginClientRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<JarandClient> getClients(UUID ownerId) {
        return jdbcTemplate.query(
                "SELECT client_id, type, display_name, owner_id, time_of_creation, client_secret, username, password FROM jarand_client WHERE owner_id = :owner_id",
                new MapSqlParameterSource("owner_id", ownerId),
                this::mapRow);
    }

    public void createSecretClient(SecretClient client) {
        jdbcTemplate.update("INSERT INTO jarand_client(client_id, type, display_name, owner_id, time_of_creation, client_secret) " +
                        "VALUES (:client_id, :type, :display_name, :owner_id, :time_of_creation, :client_secret)",
                new MapSqlParameterSource()
                        .addValue("client_id", client.getClientId())
                        .addValue("type", "SECRET")
                        .addValue("display_name", client.getDisplayName())
                        .addValue("owner_id", client.getOwnerId())
                        .addValue("time_of_creation", client.getTimeOfCreation().toString())
                        .addValue("client_secret", client.getClientSecret()));
    }

    public void createLoginClient(LoginClient client) {
        jdbcTemplate.update("INSERT INTO jarand_client(client_id, type, display_name, owner_id, time_of_creation, username, password) " +
                        "VALUES (:client_id, :type, :display_name, :owner_id, :time_of_creation, :username, :password)",
                new MapSqlParameterSource()
                        .addValue("client_id", client.getClientId())
                        .addValue("type", "LOGIN")
                        .addValue("display_name", client.getDisplayName())
                        .addValue("owner_id", client.getOwnerId())
                        .addValue("time_of_creation", client.getTimeOfCreation().toString())
                        .addValue("username", client.getUsername())
                        .addValue("password", client.getPassword()));
    }

    private JarandClient mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        final var type = resultSet.getString("type");
        return switch (type) {
            case "SECRET" -> new SecretClient(
                    resultSet.getString("client_id"),
                    type,
                    resultSet.getString("display_name"),
                    UUID.fromString(resultSet.getString("owner_id")),
                    Instant.parse(resultSet.getString("time_of_creation")),
                    resultSet.getString("client_secret"));
            case "LOGIN" -> new LoginClient(
                    resultSet.getString("client_id"),
                    type,
                    resultSet.getString("display_name"),
                    UUID.fromString(resultSet.getString("owner_id")),
                    Instant.parse(resultSet.getString("time_of_creation")),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
            default -> throw new IllegalStateException("Unexpected client type: " + type);
        };
    }

    private LoginClient mapLoginClientRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginClient(
                resultSet.getString("client_id"),
                resultSet.getString("type"),
                resultSet.getString("display_name"),
                UUID.fromString(resultSet.getString("owner_id")),
                Instant.parse(resultSet.getString("time_of_creation")),
                resultSet.getString("username"),
                resultSet.getString("password"));
    }
}
