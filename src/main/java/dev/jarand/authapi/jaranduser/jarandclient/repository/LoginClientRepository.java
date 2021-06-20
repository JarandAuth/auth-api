package dev.jarand.authapi.jaranduser.jarandclient.repository;

import dev.jarand.authapi.jaranduser.jarandclient.domain.LoginClient;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoginClientRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginClientRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createClient(LoginClient client) {
        jdbcTemplate.update("INSERT INTO login_client (client_id, username, password, owner_id, time_of_creation) " +
                        "VALUES (:client_id, :username, :password, :owner_id, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("client_id", client.getClientId())
                        .addValue("username", client.getUsername())
                        .addValue("password", client.getPassword())
                        .addValue("owner_id", client.getOwnerId())
                        .addValue("time_of_creation", client.getTimeOfCreation().toString()));
    }

    public Optional<LoginClient> getClient(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT client_id, username, password, owner_id, time_of_creation FROM login_client WHERE username = :username",
                    new MapSqlParameterSource("username", username),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public LoginClient getClient(UUID ownerId) {
        return jdbcTemplate.queryForObject(
                "SELECT client_id, username, password, owner_id, time_of_creation FROM login_client WHERE owner_id = :owner_id",
                new MapSqlParameterSource("owner_id", ownerId),
                this::mapRow);
    }

    private LoginClient mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginClient(
                resultSet.getString("client_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                UUID.fromString(resultSet.getString("owner_id")),
                Instant.parse(resultSet.getString("time_of_creation")));
    }
}
