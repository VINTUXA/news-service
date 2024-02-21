package com.example.demo.repository;

import com.example.demo.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseNewsRepository extends JpaRepository<News, Long> {
}
