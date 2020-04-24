package com.palmseung.modules.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequestView {
    private String email;
    private String name;
    private String password;

    @Builder
    public CreateUserRequestView(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static CreateUserRequestView of(String email, String name, String password){
        return CreateUserRequestView.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
