package com.dw.capstonebnform.dto;

import java.io.Serializable;

import androidx.room.Dao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Dao
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String userId;
    private String name;
    @SuppressWarnings("WeakerAccess")
    private String email;
    @FieldNameConstants.Exclude
    public boolean isAutheticated;
    @FieldNameConstants.Exclude
    private boolean isNew, isCreated;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
