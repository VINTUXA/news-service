package com.example.demo.web.model;

import lombok.Data;

@Data
public class UpsetNewsRequest {
    private Long categoryId;
    private String newsText;
}
