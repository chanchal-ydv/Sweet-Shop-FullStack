package com.sweetshop.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    @Autowired
    private SweetRepository repository;

    @Autowired
    private UserRepository userRepository;

    // GET ALL (Safe Version)
    @GetMapping
    public List<Sweet> getAll(Principal principal) {
        // SAFETY CHECK: If no one is logged in, don't crash. Just return empty list.
        if (principal == null) {
            return Collections.emptyList();
        }
        return repository.findByUserUsername(principal.getName());
    }

    // SEARCH (Safe Version)
    @GetMapping("/search")
    public List<Sweet> search(@RequestParam String query, Principal principal) {
        if (principal == null) return Collections.emptyList();
        
        List<Sweet> mySweets = repository.findByUserUsername(principal.getName());
        return mySweets.stream()
                .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()) ||
                             s.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // CREATE
    @PostMapping
    public Sweet create(@RequestBody Sweet sweet, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You must be logged in to add sweets!");
        }
        
        User currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        sweet.setUser(currentUser);
        return repository.save(sweet);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Sweet update(@PathVariable Long id, @RequestBody Sweet sweetDetails, Principal principal) {
        if (principal == null) throw new RuntimeException("Unauthorized");

        Sweet sweet = repository.findById(id).orElseThrow();
        
        if (!sweet.getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("You do not own this sweet!");
        }

        sweet.setName(sweetDetails.getName());
        sweet.setPrice(sweetDetails.getPrice());
        sweet.setQuantity(sweetDetails.getQuantity());
        sweet.setCategory(sweetDetails.getCategory());
        
        return repository.save(sweet);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        if (principal == null) throw new RuntimeException("Unauthorized");

        Sweet sweet = repository.findById(id).orElseThrow();

        if (!sweet.getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("You do not own this sweet!");
        }

        repository.deleteById(id);
    }

    // PURCHASE
    @PostMapping("/{id}/purchase")
    public Sweet purchase(@PathVariable Long id, Principal principal) {
        if (principal == null) throw new RuntimeException("Unauthorized");

        Sweet sweet = repository.findById(id).orElseThrow();

        if (!sweet.getUser().getUsername().equals(principal.getName())) {
            throw new RuntimeException("You do not own this sweet!");
        }

        if (sweet.getQuantity() > 0) {
            sweet.setQuantity(sweet.getQuantity() - 1);
            return repository.save(sweet);
        } else {
            throw new RuntimeException("Out of stock!");
        }
    }
}