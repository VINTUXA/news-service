package com.example.demo.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class News {
    private Long id;
    private String title;
    private String newsText;
    private List<Comment> comments = new ArrayList<>();
    private Category category;
    private Instant createAt;
    private Instant updateAt;

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long id){
        comments = comments.stream().filter(o -> !o.getId().equals(id)).collect(Collectors.toList());
    }

}
