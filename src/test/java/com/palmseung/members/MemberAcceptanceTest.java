package com.palmseung.members;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.palmseung.members.MemberConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberAcceptanceTest extends AbstractAcceptanceTest {
    private MemberHttpTest memberHttpTest;

    @BeforeEach
    public void setUp() {
        this.memberHttpTest = new MemberHttpTest(webTestClient);
    }

    @DisplayName("회원_가입하기")
    @Test
    public void signUp() {
        //when
        Long id = memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD)
                .getResponseBody()
                .getId();

        //then
        assertThat(id).isEqualTo(1L);
    }
}