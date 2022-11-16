package com.example.themes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Theme {
    private String title;
    private String description;
    private String username;

    private List<Comment> comments;

    public Theme(String title, String description, String username) {
        this.title = title;
        this.description = description;
        this.username = username;

        this.comments = new ArrayList<>();
    }
}
