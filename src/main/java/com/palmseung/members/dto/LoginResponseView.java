package com.palmseung.members.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseView {
    private static final String AUTH_TOKEN_TYPE = "Bearer ";

    private String accessToken;
    private String tokenType;

    public LoginResponseView(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public static LoginResponseView of(String accessToken){
        return new LoginResponseView(accessToken, AUTH_TOKEN_TYPE);
    }
}