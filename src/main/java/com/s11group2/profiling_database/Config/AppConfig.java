package com.s11group2.profiling_database.Config;

import com.s11group2.profiling_database.Model.DatabaseManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class AppConfig {
    @Bean
    public DatabaseManager databaseManager() throws SQLException {
        return DatabaseManager.getInstance();
    }
}
