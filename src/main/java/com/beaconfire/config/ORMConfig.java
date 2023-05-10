package com.beaconfire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ORMConfig {
    @Bean
    public boolean ormSwitch(){
        return true; // true for Hibernate, false for JDBC
    }

}
