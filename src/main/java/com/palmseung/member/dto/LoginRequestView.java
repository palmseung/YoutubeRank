package com.palmseung.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestView {
    private String email;
    private String password;
    private String accessToken;
    private String tokenType;

    public LoginRequestView() {
    }

    @Builder
    public LoginRequestView(String email, String password,
                            String accessToken, String tokenType) {
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
