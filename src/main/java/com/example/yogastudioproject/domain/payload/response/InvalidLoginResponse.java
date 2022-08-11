package com.example.yogastudioproject.domain.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String email;
    private String password;

    public InvalidLoginResponse() {
        this.email = "Invalid email";
        this.password = "Invalid password";
    }
}
