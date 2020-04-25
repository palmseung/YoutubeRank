package com.palmseung.members.acceptancetest;

import com.palmseung.AbstractAcceptanceTest;
import com.palmseung.members.domain.MemberRole;
import com.palmseung.members.dto.MemberResponseView;
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

    @DisplayName("회원 가입")
    @Test
    public void signUp() {
        //when
        MemberResponseView response = createMember();

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(response.getPassword()).contains("bcrypt");
        assertThat(response.getMemberRole()).isEqualTo(MemberRole.USER);
    }

    @DisplayName("회원 탈퇴")
    @Test
    public void unsubscribe() {
        //given
        Long id = createMember().getId();

        //when, then
        webTestClient.delete().uri(BASE_URI_USER_API + "/" + id)
                .exchange()
                .expectStatus().isNoContent();
    }

    private MemberResponseView createMember() {
        return memberHttpTest.createMember(TEST_EMAIL, TEST_NAME, TEST_PASSWORD).getResponseBody();
    }
}