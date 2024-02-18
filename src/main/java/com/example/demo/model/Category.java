package com.example.demo.model;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Category {
    private Long id;
    private String name;
    private List<News> newsList = new ArrayList<>();
    private Instant createAt;
    private Instant updateAt;

    public void addNews(News news){
        newsList.add(news);
    }

    public void removeNews(Long id){
        newsList = newsList.stream().filter(o -> !o.getId().equals(id)).collect(Collectors.toList());
    }
}
