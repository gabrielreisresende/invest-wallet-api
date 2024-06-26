package com.resendegabriel.investwalletapi.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    private static final String CLIENT_ROLE = "CLIENT";
    private static final String ADMIN_ROLE = "ADMIN";

    private static final String CLIENTS_ENDPOINT = "/clients/**";
    private static final String WALLETS_ENDPOINT = "/wallets/**";
    private static final String ACTIVE_TYPE_ENDPOINT = "/active-types/**";
    private static final String ACTIVE_SECTOR_ENDPOINT = "/active-sectors/**";
    private static final String ACTIVE_CODE_ENDPOINT = "/active-codes/**";
    private static final String ACTIVES_ENDPOINT = "/actives/**";
    private static final String REPORTS_ENDPOINT = "/export/reports/pdf/**";

    private static final String[] WHITE_AUTH_LIST = {
            "/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_AUTH_LIST).permitAll()

                        .requestMatchers(HttpMethod.POST, CLIENTS_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.PUT, CLIENTS_ENDPOINT).hasRole(CLIENT_ROLE)
                        .requestMatchers(HttpMethod.GET, CLIENTS_ENDPOINT).hasRole(CLIENT_ROLE)
                        .requestMatchers(HttpMethod.DELETE, CLIENTS_ENDPOINT).hasRole(CLIENT_ROLE)

                        .requestMatchers(WALLETS_ENDPOINT).hasRole(CLIENT_ROLE)

                        .requestMatchers(HttpMethod.POST, ACTIVE_TYPE_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, ACTIVE_TYPE_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, ACTIVE_TYPE_ENDPOINT).hasRole(ADMIN_ROLE)

                        .requestMatchers(HttpMethod.POST, ACTIVE_SECTOR_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, ACTIVE_SECTOR_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, ACTIVE_SECTOR_ENDPOINT).hasRole(ADMIN_ROLE)

                        .requestMatchers(HttpMethod.POST, ACTIVE_CODE_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, ACTIVE_CODE_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, ACTIVE_CODE_ENDPOINT).hasRole(ADMIN_ROLE)

                        .requestMatchers(ACTIVES_ENDPOINT).hasRole(CLIENT_ROLE)

                        .requestMatchers(REPORTS_ENDPOINT).hasRole(CLIENT_ROLE)

                        .anyRequest().authenticated())

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
