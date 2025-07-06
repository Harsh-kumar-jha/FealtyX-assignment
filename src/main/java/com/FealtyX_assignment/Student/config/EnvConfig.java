package com.FealtyX_assignment.Student.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import static com.FealtyX_assignment.Student.utils.HelperMethods.isEmpty;

@Configuration
public class EnvConfig {
    
    @Autowired
    private Environment springEnv;

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")  
                .ignoreIfMissing()
                .load();

        // Set system properties for Spring to pick up
        dotenv.entries().forEach(entry -> {
            if (Boolean.parseBoolean(System.getProperty(String.valueOf(isEmpty(entry.getKey()))))) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
    }
} 