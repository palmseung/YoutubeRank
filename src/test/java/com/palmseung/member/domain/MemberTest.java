package com.palmseung.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.palmseung.member.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {
    @DisplayName("Member객체가 생성될 때, createDate/modifiedDate field도 생성된다. ")
    @Test
    void validateCreatedAndModifiedDate() {
        //when
        Member member = Member.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();

        //then
        assertThat(member).hasFieldOrProperty("createdDate");
        assertThat(member).hasFieldOrProperty("modifiedDate");
    }
}