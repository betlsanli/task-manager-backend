package com.example.tm.config;

import com.example.tm.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // for simplicity (enable in production with proper token handling)
                /*.formLogin(login -> login
                        .loginPage("/auth/login")
                        .permitAll() // Allow everyone to access login
                )*/
                .authorizeHttpRequests((requests) -> requests
                        //.requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER") // User or admin endpoints
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/logout").permitAll()
                        .anyRequest().authenticated() // Other requests must be authenticated
                );
                /*.logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Clear cookies
                        .permitAll()
                );*/

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
