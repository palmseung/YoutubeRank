package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;
    private Role role;

    @Builder
    public CreateMemberRequestView(String email, String name, String password, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public CreateMemberRequestView changePassword(String encodedPassword){
        this.password = encodedPassword;
        return this;
    }

    public CreateMemberRequestView assginRole(Role role){
        this.role = role;
        return this;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(role)
                .build();
    }
}
