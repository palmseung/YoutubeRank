package com.palmseung.members.domain;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private String memberRole;

    Role(String role) {
        this.memberRole = role;
    }
}