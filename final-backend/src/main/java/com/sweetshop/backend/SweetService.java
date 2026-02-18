package com.sweetshop.backend;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SweetService {
    @Autowired private SweetRepository repository;

    public Sweet addSweet(Sweet sweet) {
        return repository.save(sweet);
    }
    
    public List<Sweet> getAllSweets() {
        return repository.findAll();
    }
}