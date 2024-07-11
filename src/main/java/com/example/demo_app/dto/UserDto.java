package com.example.demo_app.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private boolean isAccountNonExpired;
    private boolean isEnabled;
}
