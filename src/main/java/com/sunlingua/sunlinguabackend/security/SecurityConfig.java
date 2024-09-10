package com.sunlingua.sunlinguabackend.security;

import com.sunlingua.sunlinguabackend.config.JwtAuthenticationFilter;
import com.sunlingua.sunlinguabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.sunlingua.sunlinguabackend.user.Permission.*;
import static com.sunlingua.sunlinguabackend.user.Permission.CREATOR_DELETE;
import static com.sunlingua.sunlinguabackend.user.Role.*;
import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(GET, "/api/v1/resources").hasAnyRole(USER.name(), PREMIUM_USER.name(),ADMIN.name(), CREATOR.name())
                                //.requestMatchers(GET,"/api/v1/resources").hasAnyAuthority(USER_READ.name(), ADMIN_READ.name(), PREMIUM_USER_READ.name(),CREATOR_READ.name())
                                //.requestMatchers(POST, "/api/v1/resources/**").hasAnyAuthority(ADMIN_CREATE.name(), CREATOR_CREATE.name())
                                .requestMatchers(POST, "/api/v1/resources/**").hasAnyAuthority(ADMIN_CREATE.getPermission(), CREATOR_CREATE.getPermission())

                                .requestMatchers(PUT, "/api/v1/resources/**").hasAnyAuthority(ADMIN_UPDATE.name(), CREATOR_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/resources/**").hasAnyAuthority(ADMIN_DELETE.name(), CREATOR_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}