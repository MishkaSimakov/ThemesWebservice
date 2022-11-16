package com.example.themes.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Theme extends EntityWithTimestamps {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "theme")
    private List<Comment> comments;
}
