package com.palmseung.members.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {
    @DisplayName("Member객체가 생성될 때, createDate()값이 정해진다. ")
    @Test
    void validateCreatedDate() {
        //when
        Member member = Member.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();

        //then
        assertThat(member).hasFieldOrProperty("createdDate");
    }
}
