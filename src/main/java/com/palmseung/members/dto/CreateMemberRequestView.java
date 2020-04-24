package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;

    @Builder
    public CreateMemberRequestView(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static CreateMemberRequestView of(String email, String name, String password){
        return CreateMemberRequestView.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

    public CreateMemberRequestView changePassword(String encodedPassword){
        this.password = encodedPassword;
        return this;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
