package com.example.demo.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String userName;
    private List<CommentResponse> comments = new ArrayList<>();
}
