package dev.jarand.authapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .mvcMatchers(HttpMethod.POST, "/jarand-user") // TODO: For testing purposes only.
                .mvcMatchers(HttpMethod.POST, "/jarand-user/{id}/jarand-client") // TODO: For testing purposes only.
                .mvcMatchers(HttpMethod.POST, "/scope") // TODO: For testing purposes only.
                .mvcMatchers(HttpMethod.GET, "/scope") // TODO: For testing purposes only.
                .mvcMatchers(HttpMethod.POST, "/scope/{id}/connection") // TODO: For testing purposes only.
                .mvcMatchers(HttpMethod.GET, "/key/public")
                .mvcMatchers(HttpMethod.POST, "/oauth/token");
    }
}
