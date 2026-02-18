package com.sweetshop.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    // We removed the Repositories because we don't need to add anything anymore
    
    @Override
    public void run(String... args) throws Exception {
        // Intentionally left empty.
        // This ensures the shop starts with 0 items.
        System.out.println("✅ DataSeeder finished: No default sweets added.");
    }
}