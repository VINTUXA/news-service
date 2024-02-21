package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)// маппед указывает на обратное отображение к отношение многие к одномк
    @ToString.Exclude // каскад указывает какие оперции по сохра обновлению и тд должны быть применены к связанной сущности
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Long commentId){
        comments = comments.stream().filter(o -> !o.getId().equals(commentId)).collect(Collectors.toList());
    }
}
