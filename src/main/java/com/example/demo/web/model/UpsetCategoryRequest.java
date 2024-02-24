package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsetCategoryRequest {
    @NotBlank(message = "categoryName name should be specified in the request")
    private String categoryName;
}
