package com.palmseung.members.dto;

import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequestView {
    private String email;
    private String name;
    private String password;
    private MemberRole memberRole;

    @Builder
    public CreateMemberRequestView(String email, String name, String password, MemberRole memberRole) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.memberRole = memberRole;
    }

    public CreateMemberRequestView changePassword(String encodedPassword){
        this.password = encodedPassword;
        return this;
    }

    public CreateMemberRequestView assginRole(MemberRole memberRole){
        this.memberRole = memberRole;
        return this;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .memberRole(memberRole)
                .build();
    }
}
