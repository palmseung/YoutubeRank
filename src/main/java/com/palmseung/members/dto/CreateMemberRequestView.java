package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;
    private List<String> roles = new ArrayList<>();

    public CreateMemberRequestView() {
    }

    @Builder
    public CreateMemberRequestView(String email, String name, String password, List<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public CreateMemberRequestView changePassword(String encodedPassword) {
        this.password = encodedPassword;
        return this;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .roles(roles)
                .build();
    }
}
