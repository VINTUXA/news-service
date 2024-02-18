package com.example.demo.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Comment {
    private Long id;
    private String commentText;
    private User author;
    private News news;
    private Instant createAt;
    private Instant updateAt;

}
