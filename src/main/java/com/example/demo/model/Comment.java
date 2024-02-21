package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentText;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @ToString.Exclude
    private User user;
    @ManyToOne // связь будет м т о
    @JoinColumn(name = "user_id") //
    @ToString.Exclude //
    // private User author;
    private News news;
    @CreationTimestamp
    private Instant createAt;
    @UpdateTimestamp
    private Instant updateAt;

}
