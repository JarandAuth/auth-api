package dev.jarand.authapi.token.repository;

import dev.jarand.authapi.token.domain.RefreshToken;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@Repository
public class TokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TokenRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRefreshToken(String jti, String subject, Instant issuedAt) {
        jdbcTemplate.update(
                "INSERT INTO refresh_token (jti, subject, issued_at) VALUES (:jti, :subject, :issued_at)",
                new MapSqlParameterSource()
                        .addValue("jti", jti)
                        .addValue("subject", subject)
                        .addValue("issued_at", issuedAt.toString()));
    }

    public List<RefreshToken> getRefreshTokens() {
        return jdbcTemplate.query("SELECT jti, subject, issued_at FROM refresh_token", this::mapRow);
    }

    public boolean exists(String jti) {
        final var count = jdbcTemplate.queryForObject("SELECT count(jti) FROM refresh_token WHERE jti = :jti", new MapSqlParameterSource("jti", jti), Integer.class);
        return count != null && count == 1;
    }

    private RefreshToken mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RefreshToken(resultSet.getString("jti"), resultSet.getString("subject"), Instant.parse(resultSet.getString("issued_at")));
    }
}
