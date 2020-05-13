package com.palmseung.members.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateMemberRequestView {
    private String newPassword;

    @Builder
    public UpdateMemberRequestView(String newPassword) {
        this.newPassword = newPassword;
    }
}