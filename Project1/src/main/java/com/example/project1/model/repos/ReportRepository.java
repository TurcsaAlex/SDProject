package com.example.project1.model.repos;

import com.example.project1.model.Product;
import com.example.project1.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Integer> {
    List<Report> findAllByOrderBySold();
    Optional<Report> findByProduct(Product product);
}
