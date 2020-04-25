package com.palmseung.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    public static final String WARNING_MEMBER_EXISTING_EMAIL = "The E-mail is already in use!";
    public static final String WARNING_MEMBER_INVALID_MEMBER = "The member is invalid!";
    public static final String WARNING_JWT_INVALID_TOKEN = "Expired or invalid JWT token";
}
