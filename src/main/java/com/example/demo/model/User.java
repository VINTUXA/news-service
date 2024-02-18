package com.example.demo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class User {
    private Long id;
    private String name;
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream().filter(o -> !o.getId().equals(commentId)).collect(Collectors.toList());
    }
}
