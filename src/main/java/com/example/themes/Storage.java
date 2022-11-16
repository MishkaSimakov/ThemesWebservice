package com.example.themes;

import com.example.themes.models.Comment;
import com.example.themes.models.Theme;
import com.example.themes.models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Storage {
    private final List<Theme> themes = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public Boolean addUser(User user) {
        if (!checkUser(user))
            return false;

        return users.add(user);
    }

    public Boolean addUser(Integer index, User user) {
        if (!checkUser(user))
            return false;

        users.add(index, user);
        return true;
    }

    public Boolean addTheme(Theme theme) {
        if (!checkTheme(theme))
            return false;

        return themes.add(theme);
    }

    public Boolean addTheme(Integer index, Theme theme) {
        if (!checkTheme(theme))
            return false;

        themes.add(index, theme);

        return true;
    }

    public Boolean addComment(Theme theme, Comment comment) {
        if (!checkComment(theme, comment))
            return false;

        return theme.getComments().add(comment);
    }

    public Boolean addComment(Theme theme, Integer commentIndex, Comment comment) {
        if (!checkComment(theme, comment))
            return false;

        theme.getComments().add(commentIndex, comment);

        return true;
    }

    public List<Comment> getUserComments(User user) {
        List<Comment> comments = new ArrayList<>();

        for (Theme theme : themes) {
            comments.addAll(
                    theme.getComments().stream()
                            .filter(comment -> comment.getUsername().equals(user.getName()))
                            .toList()
            );
        }

        return comments;
    }

    public List<Theme> getUserThemes(User user) {
        return themes.stream().filter(theme -> theme.getUsername().equals(user.getName())).toList();
    }

    private Boolean checkUser(User user) {
        return users.stream().noneMatch(u -> u.getName().equals(user.getName()));
    }

    private Boolean checkTheme(Theme theme) {
        return users.stream().anyMatch(user -> theme.getUsername().equals(user.getName()));
    }

    private Boolean checkComment(Theme theme, Comment comment) {
        if (users.stream().noneMatch(user -> user.getName().equals(comment.getUsername())))
            return false;

        return theme.getComments().stream().noneMatch(c -> c.getUsername().equals(comment.getUsername()));
    }

    public void removeCommentsOf(User user) {
        for (Theme theme : themes) {
            theme.getComments().removeIf(comment -> comment.getUsername().equals(user.getName()));
        }
    }
}
