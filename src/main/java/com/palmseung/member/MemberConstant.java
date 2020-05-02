package com.palmseung.member;

import com.palmseung.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConstant {
    public static final String BASE_URI_MEMBER_API = "/api/members";
    public static final String BASE_URI_LOGIN_API = "/api/members/login";
    public static final String BASE_URI_MY_INFO_API = "/api/members/my-info";

    public static final Long TEST_ID = 1l;
    public static final String TEST_EMAIL = "user@email.com";
    public static final String TEST_NAME = "user";
    public static final String TEST_PASSWORD = "password";

    public static final Member TEST_MEMBER = Member.builder()
            .id(TEST_ID)
            .email(TEST_EMAIL)
            .name(TEST_NAME)
            .password(TEST_PASSWORD)
            .build();
}
