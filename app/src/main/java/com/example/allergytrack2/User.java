package com.example.allergytrack2;

public class User {public String userId, name, email, profileImage;

    // Default constructor required for Firebase
    public User() {}

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
