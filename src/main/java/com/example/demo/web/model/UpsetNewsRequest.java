package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpsetNewsRequest {
    @NotNull(message = "categoryId should be specified in the request")
    private Long categoryId;
    @NotBlank(message = "newsText should be specified in the request")
    private String newsText;
    @NotBlank(message = "title should be specified in the request")
    private String title;
    @NotNull(message = "creatorId should be specified in the request")
    private Long creatorId;
}
