package com.example.demo.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsResponseForOneNews {
    private Long id;
    private String newsText;

    private Long authorId;

    private Long categoryId;
    private String title;

    private Integer commentCount;

    private List<CommentResponse> comments = new ArrayList<>();;
}
