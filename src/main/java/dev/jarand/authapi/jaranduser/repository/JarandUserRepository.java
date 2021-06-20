package dev.jarand.authapi.jaranduser.repository;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
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
public class JarandUserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JarandUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<JarandUser> getUser(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, email, username, display_name, time_of_creation FROM jarand_user WHERE id = :id",
                    new MapSqlParameterSource("id", id),
                    this::mapRow));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void createUser(JarandUser user) {
        jdbcTemplate.update("INSERT INTO jarand_user(id, email, username, display_name, time_of_creation) " +
                        "VALUES (:id, :email, :username, :display_name, :time_of_creation)",
                new MapSqlParameterSource()
                        .addValue("id", user.getId())
                        .addValue("email", user.getEmail())
                        .addValue("username", user.getUsername())
                        .addValue("display_name", user.getDisplayName())
                        .addValue("time_of_creation", user.getTimeOfCreation().toString()));
    }

    private JarandUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new JarandUser(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("email"),
                resultSet.getString("username"),
                resultSet.getString("display_name"),
                Instant.parse(resultSet.getString("time_of_creation")));
    }
}
