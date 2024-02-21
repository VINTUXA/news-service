package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_name")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)// маппед указывает на обратное отображение к отношение многие к одномк
    @ToString.Exclude // каскад указывает какие оперции по сохра обновлению и тд должны быть применены к связанной сущности
    @Builder.Default
    private List<News> newsList = new ArrayList<>();
    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    private Instant updateAt;

    public void addNews(News news){
        newsList.add(news);
    }

    public void removeNews(Long id){
        newsList = newsList.stream().filter(o -> !o.getId().equals(id)).collect(Collectors.toList());
    }
}
