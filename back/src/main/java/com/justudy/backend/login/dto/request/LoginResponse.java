package com.justudy.backend.login.dto.request;

import lombok.Data;

@Data
public class LoginResponse {

    private Long loginSequence;

    private String password;
    private String nickname;

    public LoginResponse(String nickname) {
        this.nickname = nickname;
    }
}

