package com.pizzariamarqlinda.api_pizzaria_marqlinda.config;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityConfiguration(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, "/api/users").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN_USER");
                    authorize.requestMatchers("/api/roles/**").hasRole("ADMIN_USER");
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return new CustomUserDetailService(userService);
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }
}
