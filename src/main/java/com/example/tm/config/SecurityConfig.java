package com.example.tm.config;

import com.example.tm.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity// Enable method-level security
@EnableAspectJAutoProxy
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;


    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // for simplicity (enable in production with proper token handling)
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Use session if required
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/**").permitAll()
                        //.requestMatchers("/user/all-users").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll() // Other requests must be authenticated
                )
                .authenticationManager(authenticationManager);
                /*.logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Clear cookies
                        .logoutSuccessUrl("/my/success/endpoint")
                        .permitAll()
                );*/

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
