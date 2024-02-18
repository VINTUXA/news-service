package com.example.demo.web.model;

import lombok.Data;

@Data
public class UpsetCommentRequest {
    private Long userid;
    private String commentText;
    private Long newsId;

}
