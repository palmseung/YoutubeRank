package com.palmseung.members.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMemberRequestView {
    private Long id;
    private String name;
    private String password;

    public UpdateMemberRequestView() {
    }

    @Builder
    public UpdateMemberRequestView(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
