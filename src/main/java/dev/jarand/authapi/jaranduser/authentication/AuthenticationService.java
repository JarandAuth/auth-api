package dev.jarand.authapi.jaranduser.authentication;

import dev.jarand.authapi.jaranduser.JarandUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final JarandUserService jarandUserService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(JarandUserService jarandUserService, PasswordEncoder passwordEncoder) {
        this.jarandUserService = jarandUserService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        logger.info("Trying to authenticate user with username: {}", username);
        final var optionalUser = jarandUserService.getUserByUsername(username);
        if (optionalUser.isEmpty()) {
            logger.info("Authentication failed (no user found) for username: {}", username);
            return false;
        }
        final var user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.info("Authentication failed (invalid password) for username: {}", username);
            return false;
        }
        logger.info("Authentication successful for username: {}", username);
        return true;
    }
}
