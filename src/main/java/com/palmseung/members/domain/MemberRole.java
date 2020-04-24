package com.palmseung.members.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String memberRole;

    MemberRole(String role) {
        this.memberRole = role;
    }
}