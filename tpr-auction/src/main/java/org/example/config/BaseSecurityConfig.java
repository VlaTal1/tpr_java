package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public abstract class BaseSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http = baseConfigure(http);
        additionalConfigure(http);
        return http.build();
    }

    protected HttpSecurity baseConfigure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/**").permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http;
    }
    protected abstract void additionalConfigure(HttpSecurity http) throws Exception;
}
