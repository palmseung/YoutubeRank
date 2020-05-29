package com.palmseung.modules.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.palmseung.infra.properties.AdminProperties;
import com.palmseung.modules.admin.dto.AdminMemberRequestView;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.admin.service.AdminService;
import com.palmseung.modules.members.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiAdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AdminProperties adminProperties;

    @DisplayName("어드민 계정 생성 요청시, 서버에서 지정한 메일과 비밀번호인 경우 OK 반환")
    @Test
    void createAccount() throws Exception {
        //given
        given(adminProperties.canBeAdmin(anyString(), anyString())).willReturn(true);
        given(adminService.createAdmin(any(Member.class))).willReturn(AdminMemberResponseView.of(TEST_MEMBER));

        //when, then
        mockMvc.perform(post("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAdminRequest())))
                .andExpect(status().isOk());
    }

    @DisplayName("어드민 계정 생성 요청시, 서버에서 지정한 메일과 비밀번호가 아니면 Forbidden 반환")
    @Test
    void createAccountWhenInvalidMemberInfo() throws Exception {
        //given
        given(adminProperties.canBeAdmin(anyString(), anyString()))
                .willReturn(false);

        //when, then
        mockMvc.perform(post("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createAdminRequest())))
                .andExpect(status().isForbidden());
    }

    private AdminMemberRequestView createAdminRequest() {
        return AdminMemberRequestView.builder()
                .email("email@email.com")
                .name("name")
                .password("password")
                .build();
    }
}