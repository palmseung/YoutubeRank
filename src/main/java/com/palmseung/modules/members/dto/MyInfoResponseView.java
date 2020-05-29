package com.palmseung.modules.members.dto;

import com.palmseung.modules.members.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyInfoResponseView {
    private String email;
    private String name;
    private String password;

    @Builder
    public MyInfoResponseView(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static MyInfoResponseView of(Member member) {
        return MyInfoResponseView.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .build();
    }
}
