package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequestView {
    private String email;
    private String password;
    private String accessToken;
    private String tokenType;

    @Builder
    public LoginRequestView(String email, String password,
                            String accessToken, String tokenType) {
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public static LoginRequestView of(Member member){
        return LoginRequestView.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}