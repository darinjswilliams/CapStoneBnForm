package com.dw.capstonebnform.dto;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class User implements Serializable {
    public String uid;
    public  String name;

    @SuppressWarnings("WeakerAccess")
    public String email;

    @Exclude
    public boolean isAuthenticated, isNew, isCreated;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}
