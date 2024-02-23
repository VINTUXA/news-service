package com.example.demo.web.model;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private Long authorId;
    private Long categoryId;

}
