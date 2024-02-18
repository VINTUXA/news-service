package com.example.demo.web.model;

import com.example.demo.model.News;
import com.example.demo.model.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CommentResponse {
    private Long id;
    private String commentText;
    private Long newsId;

}
