package com.palmseung.modules.users;

import com.palmseung.modules.users.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstant {
    public static final String BASE_URI_USER = "/users";
    public static final Long TEST_ID = 1l;
    public static final String TEST_EMAIL = "user@email.com";
    public static final String TEST_NAME = "user";
    public static final String TEST_PASSWORD = "password";

    public static final User TEST_USER = User.builder()
            .id(TEST_ID)
            .email(TEST_EMAIL)
            .name(TEST_NAME)
            .password(TEST_PASSWORD)
            .build();
}
