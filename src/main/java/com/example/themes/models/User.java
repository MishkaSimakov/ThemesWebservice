package com.example.themes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Theme> themes;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
}
