package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsetCommentRequest {
    @NotBlank(message = "userId name should be specified in the request")
    private Long userId;
    @NotBlank(message = "commentText name should be specified in the request")
    private String commentText;
    @NotBlank(message = "newsId name should be specified in the request")
    private Long newsId;

}
