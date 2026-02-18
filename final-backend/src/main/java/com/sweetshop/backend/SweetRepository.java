package com.sweetshop.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SweetRepository extends JpaRepository<Sweet, Long> {
    // Finds all sweets that belong to a specific user
    List<Sweet> findByUserUsername(String username);
}