package com.palmseung.members.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateMemberRequestView {
    private String newName;
    private String newPassword;

    @Builder
    public UpdateMemberRequestView(String newName, String newPassword) {
        this.newName = newName;
        this.newPassword = newPassword;
    }
}