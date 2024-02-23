package com.example.demo.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryFilter {
    private Integer pageSize;
    private Integer pageNumber;
}
