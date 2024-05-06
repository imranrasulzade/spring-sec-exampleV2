package com.rsl.springsecexamplev2.configuration;

import com.rsl.springsecexamplev2.filter.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {


    private final JwtAuthorizationFilter jwtAuthorizationFilter;




    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(permitAllUrls).permitAll()
                                .requestMatchers(adminUrls).hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(clientUrls).hasAnyAuthority("ROLE_USER")
                                .requestMatchers(anyAuthUrls).authenticated()
//                                .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                        )
                );
                return http.build();

    }

    static String[] permitAllUrls = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/auth/**"
    };

    static String[] adminUrls = {
            "/controller/admin"
    };

    static String[] clientUrls = {
            "/controller/user"
    };

    static String[] anyAuthUrls = {
            "/controller/any"
    };

}
