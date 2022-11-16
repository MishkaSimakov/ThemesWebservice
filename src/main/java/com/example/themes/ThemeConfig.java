package com.example.themes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThemeConfig {
    @Bean
    public Storage accountRepository() {
        return new Storage();
    }
}
