package com.palmseung.members.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberRequestView {
    private String name;
    private String password;

    public UpdateMemberRequestView() {
    }

    @Builder
    public UpdateMemberRequestView(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
