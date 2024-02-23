package com.example.demo.web.model;

import com.example.demo.model.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsResponse {
    private Long id;
    private String newsText;

    private Long authorId;

    private Long categoryId;

    private List<CommentResponse> comments = new ArrayList<>();;
}
