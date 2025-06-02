package com.example.exercice.bean;

import com.example.exercice.entity.User;
import com.example.exercice.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {
    @Inject
    private UserService userService;


    private User user = new User(); // Current user for add/update
    private List<User> users;       // List of all users

    @PostConstruct
    public void init() {
        users = userService.findAll(); // Load users when bean is created
    }

    public void addUser() {
        userService.create(user);
        users = userService.findAll(); // Refresh list
        user = new User();             // Clear form
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
