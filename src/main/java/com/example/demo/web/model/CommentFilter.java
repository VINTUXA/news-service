package com.example.demo.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentFilter {
    private Long newsId;
    private Integer pageSize;
    private Integer pageNumber;
}
