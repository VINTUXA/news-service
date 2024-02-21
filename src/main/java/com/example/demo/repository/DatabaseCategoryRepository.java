package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseCategoryRepository extends JpaRepository<Category, Long> {
}
