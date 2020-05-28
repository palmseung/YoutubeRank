package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class AdminMemberRequestView {
    private String email;
    private String name;
    private String password;
    private List<String> roles = new ArrayList<>();

    public AdminMemberRequestView() {
    }

    @Builder
    public AdminMemberRequestView(String email, String name, String password, List<String> roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public AdminMemberRequestView of(Member member) {
        return AdminMemberRequestView.builder()
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRoles())
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .roles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
                .build();

    }
}
