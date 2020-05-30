package com.palmseung.modules.admin.controller;

import com.palmseung.BaseAcceptanceTest;
import com.palmseung.infra.properties.AdminProperties;
import com.palmseung.modules.admin.dto.AdminMemberRequestView;
import com.palmseung.modules.members.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.palmseung.modules.admin.AdminConstant.BASE_URI_ADMIN;
import static com.palmseung.modules.members.MemberConstant.*;
import static org.mockito.BDDMockito.given;

public class AdminAcceptanceTest extends BaseAcceptanceTest {
    @Autowired
    public MemberRepository memberRepository;

    @SpyBean
    private AdminProperties adminProperties;

    @AfterEach
    public void cleanDatabase() {
        memberRepository.deleteAll();
    }

    @DisplayName("어드민 계정 회원 가입 - 정상(허가된 이메일/비밀번호)")
    @Test
    public void createAdmin() {
        //given
        given(adminProperties.canBeAdmin(TEST_EMAIL, TEST_PASSWORD)).willReturn(true);
        AdminMemberRequestView request = createAdminRequest();

        //when, then
        webTestClient.post().uri(BASE_URI_ADMIN)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @DisplayName("어드민 계정 회원 가입 - 비정상(허가되지 않은 이메일/비밀번호)")
    @Test
    public void createAdminWhenInvalidEmailOrPassword() {
        //given
        given(adminProperties.canBeAdmin(TEST_EMAIL, TEST_PASSWORD)).willReturn(false);
        AdminMemberRequestView request = createAdminRequest();

        //when, then
        webTestClient.post().uri(BASE_URI_ADMIN)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isForbidden();
    }

    private AdminMemberRequestView createAdminRequest() {
        return AdminMemberRequestView.builder()
                .name(TEST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }
}