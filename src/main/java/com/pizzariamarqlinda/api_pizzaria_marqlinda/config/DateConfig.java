package com.pizzariamarqlinda.api_pizzaria_marqlinda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class DateConfig {

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
