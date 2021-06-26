package dev.jarand.authapi.security;

import dev.jarand.authapi.token.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(GET, "/jarand-user").access("hasRole('Auth.ViewUsers') or hasRole('Auth.ManageUsers') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/jarand-user/{id}").access("hasRole('Auth.ViewUsers') or hasRole('Auth.ManageUsers') or hasRole('Auth.Admin')")
                .mvcMatchers(POST, "/jarand-client").access("hasRole('Auth.ManageClients') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/jarand-client").access("hasRole('Auth.ViewClients') or hasRole('Auth.ManageClients') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/jarand-client/{clientId}").access("hasRole('Auth.ViewClients') or hasRole('Auth.ManageClients') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/scope").access("hasRole('Auth.ViewScopes') or hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/scope/{id}").access("hasRole('Auth.ViewScopes') or hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .mvcMatchers(POST, "/scope").access("hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/scope/{id}/connection").access("hasRole('Auth.ViewScopes') or hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .mvcMatchers(GET, "/scope/{id}/connection/{clientId}").access("hasRole('Auth.ViewScopes') or hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .mvcMatchers(POST, "/scope/{id}/connection").access("hasRole('Auth.ManageScopes') or hasRole('Auth.Admin')")
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .csrf().disable()
                .addFilterBefore(new BearerAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .mvcMatchers(GET, "/key/public")
                .mvcMatchers(POST, "/oauth/token")
                .mvcMatchers(POST, "/jarand-user");
    }
}
