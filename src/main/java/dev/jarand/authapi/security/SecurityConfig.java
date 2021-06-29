package dev.jarand.authapi.security;

import dev.jarand.authapi.token.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

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
                .mvcMatchers(GET, "/jarand-user").access("hasAnyAuthority('Auth.ViewUsers', 'Auth.ManageUsers', 'Auth.Admin')")
                .mvcMatchers(GET, "/jarand-user/{id}").access("hasAnyAuthority('Auth.ViewUsers', 'Auth.ManageUsers', 'Auth.Admin')")
                .mvcMatchers(POST, "/jarand-client").access("hasAnyAuthority('Auth.ManageClients', 'Auth.Admin')")
                .mvcMatchers(GET, "/jarand-client").access("hasAnyAuthority('Auth.ViewClients', 'Auth.ManageClients', 'Auth.Admin')")
                .mvcMatchers(GET, "/jarand-client/{clientId}").access("hasAnyAuthority('Auth.ViewClients', 'Auth.ManageClients', 'Auth.Admin')")
                .mvcMatchers(POST, "/grant-type").access("hasAnyAuthority('Auth.ManageGrantTypes', 'Auth.Admin')")
                .mvcMatchers(GET, "/grant-type").access("hasAnyAuthority('Auth.ViewGrantTypes', 'Auth.ManageGrantTypes', 'Auth.Admin')")
                .mvcMatchers(GET, "/grant-type/{grantType}").access("hasAnyAuthority('Auth.ViewGrantTypes', 'Auth.ManageGrantTypes', 'Auth.Admin')")
                .mvcMatchers(GET, "/granted-type").access("hasAnyAuthority('Auth.ViewGrantTypes', 'Auth.ManageGrantTypes','Auth.Admin')")
                .mvcMatchers(POST, "/granted-type").access("hasAnyAuthority('Auth.ManageGrantTypes', 'Auth.Admin')")
                .mvcMatchers(GET, "/scope").access("hasAnyAuthority('Auth.ViewScopes', 'Auth.ManageScopes', 'Auth.Admin')")
                .mvcMatchers(GET, "/scope/{id}").access("hasAnyAuthority('Auth.ViewScopes', 'Auth.ManageScopes', 'Auth.Admin')")
                .mvcMatchers(POST, "/scope").access("hasAnyAuthority('Auth.ManageScopes', 'Auth.Admin')")
                .mvcMatchers(GET, "/scope-connection").access("hasAnyAuthority('Auth.ViewScopes', 'Auth.ManageScopes', 'Auth.Admin')")
                .mvcMatchers(POST, "/scope-connection").access("hasAnyAuthority('Auth.ManageScopes', 'Auth.Admin')")
                .mvcMatchers(GET, "/refresh-token").access("hasAnyAuthority('Auth.ViewTokens', 'Auth.Admin')")
                .mvcMatchers(GET, "/token-data").authenticated()
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .csrf().disable()
                .addFilterBefore(new BearerAuthenticationFilter(tokenService), RequestCacheAwareFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .mvcMatchers(GET, "/key/public")
                .mvcMatchers(POST, "/oauth/token")
                .mvcMatchers(POST, "/jarand-user");
    }
}
