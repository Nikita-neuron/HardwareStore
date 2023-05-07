package com.mirea.hardwarestore.config;

import com.mirea.hardwarestore.security.jwt.JwtConfigurer;
import com.mirea.hardwarestore.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String USER_ENDPOINT = "/api/users/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(LOGIN_ENDPOINT).permitAll()
                .requestMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/categories/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/products/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/baskets/").hasRole("ADMIN")
                .requestMatchers( "/api/baskets/id/**").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/baskets/del/**").hasRole("ADMIN")
                .requestMatchers(USER_ENDPOINT).hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        return http.build();
    }
}
