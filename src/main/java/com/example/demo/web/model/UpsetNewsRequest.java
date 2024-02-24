package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsetNewsRequest {
    @NotBlank(message = "categoryId should be specified in the request")
    private Long categoryId;
    @NotBlank(message = "newsText should be specified in the request")
    private String newsText;
    @NotBlank(message = "title should be specified in the request")
    private String title;
    @NotBlank(message = "creatorId should be specified in the request")
    private Long creatorId;
}
