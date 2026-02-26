package com.luna.warmteaandhonestreviews.config;

import com.luna.warmteaandhonestreviews.auth.CustomEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            // Spring Security should completely ignore URLs starting with /resources/
            .requestMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(basic -> basic.realmName("WTAH")
                .authenticationEntryPoint(new CustomEntryPoint()))
            .authorizeHttpRequests(
                authorize ->
                    authorize.requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder();
        UserDetails user = users
            .username("NilKim")
            .password(passwordEncoder().encode("1234"))
            .roles("ADMIN")
            .build();
        UserDetails user2 = users
            .username("NilKim2")
            .password(passwordEncoder().encode("1234"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user, user2);
    }
}
