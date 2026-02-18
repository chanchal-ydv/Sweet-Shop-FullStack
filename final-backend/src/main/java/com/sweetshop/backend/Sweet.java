package com.sweetshop.backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String category;
    private Double price;
    private Integer quantity;

    // --- NEW: Relationship to User ---
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevents infinite recursion (doesn't print user details inside sweet)
    private User user;
}