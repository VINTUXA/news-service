package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
@Entity(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @ToString.Exclude
    private User creator;
    private String title;
    @Column(name = "news_text")
    private String newsText;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)// маппед указывает на обратное отображение к отношение многие к одномк
    @ToString.Exclude // каскад указывает какие оперции по сохра обновлению и тд должны быть применены к связанной сущности
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;
    @CreationTimestamp
    private Instant createAt;
    private Instant updateAt;

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long id){
        comments = comments.stream().filter(o -> !o.getId().equals(id)).collect(Collectors.toList());
    }

}
