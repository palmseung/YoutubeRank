package com.palmseung.modules.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;
}
