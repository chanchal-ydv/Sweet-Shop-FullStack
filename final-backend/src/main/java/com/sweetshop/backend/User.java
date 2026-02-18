package com.sweetshop.backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // 'user' is a reserved word in Postgres
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    private String password;
    private String role; // "USER" or "ADMIN"
}