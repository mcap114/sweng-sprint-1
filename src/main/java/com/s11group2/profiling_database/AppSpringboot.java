package com.s11group2.profiling_database;

import com.s11group2.profiling_database.Model.DatabaseManager;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppSpringboot {
    public static void main(String[] args) {
        SpringApplication.run(AppSpringboot.class, args);
    }

    @Autowired
    private DatabaseManager databaseManager;

    @Bean
    public CommandLineRunner openBrowser() {
        return args -> {
            System.out.println("CommandLineRunner executed");

            try {
                databaseManager.createTables();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to create tables.");
            }

            new Thread(() -> {
                try {
                    System.out.println("Waiting for the server to start...");
                    Thread.sleep(1000);
                    openHomePage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        };
    }

    private void openHomePage() {
        try {
            System.out.println("Opening browser...");
            String url = "http://localhost:8080/";
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec("open " + url);
            } else if (System.getProperty("os.name").toLowerCase().contains("nix") ||
                    System.getProperty("os.name").toLowerCase().contains("nux")) {
                Runtime.getRuntime().exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
