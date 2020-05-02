package com.palmseung.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    public static final String WARNING_MEMBER_EXISTING_EMAIL = "The E-mail is already in use!";
    public static final String WARNING_MEMBER_INVALID_MEMBER = "The member is invalid!";
    public static final String WARNING_JWT_INVALID_TOKEN = "Invalid JWT token";
    public static final String WARNING_MEMBER_UNAUTHORIZED_TO_UPDATE = "Unauthorized to update!";
    public static final String WARNING_MYKEYWORD_UNAUTHORIZED_TO_READ = "Unauthorized to read!";
    public static final String WARNING_MYKEYWORD_INVALID_MYKEYWORD = "Un-registered my keyword!";
    public static final String WARNING_MYKEYWORD_UNAUTHORIZED_TO_DELETE = "Unauthorized to delete!";
}