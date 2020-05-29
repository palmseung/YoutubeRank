package com.palmseung.modules.members.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.palmseung.modules.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {
    @DisplayName("createDate/modifiedDate 생성 확인")
    @Test
    void validateCreatedAndModifiedDate() {
        //when
        Member member = new Member();

        //then
        assertThat(member).hasFieldOrProperty("createdDate");
        assertThat(member).hasFieldOrProperty("modifiedDate");
    }
}