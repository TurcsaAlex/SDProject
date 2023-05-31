package com.example.project1.model.repos;

import com.example.project1.model.Costumer;
import com.example.project1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostumerRepository extends JpaRepository<Costumer, Integer> {
    Optional<Costumer> findByName(String name);
}
