package com.arashi.edu.arashynbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        loadEnvFile();
        SpringApplication.run(Main.class, args);
    }

    private static void loadEnvFile() {
        Path envPath = Path.of(".env");
        if (!Files.exists(envPath)) {
            System.out.println(">>> .env file NOT FOUND at: " + envPath.toAbsolutePath());
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(envPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty() || line.startsWith("#")) continue;
                int idx = line.indexOf('=');
                if (idx < 0) continue;
                String key = line.substring(0, idx).strip();
                String value = line.substring(idx + 1).strip();
                // Strip BOM if present on first key
                key = key.replace("\uFEFF", "");
                if (System.getProperty(key) == null && System.getenv(key) == null) {
                    System.setProperty(key, value);
                }
            }
            System.out.println(">>> .env loaded successfully from: " + envPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println(">>> Failed to load .env: " + e.getMessage());
        }
    }
}