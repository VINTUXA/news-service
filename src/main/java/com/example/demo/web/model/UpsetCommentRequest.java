package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpsetCommentRequest {
    @NotNull(message = "userId name should be specified in the request")
    private Long userId;
    @NotBlank(message = "commentText name should be specified in the request")
    private String commentText;
    @NotNull(message = "newsId name should be specified in the request")
    private Long newsId;

}
