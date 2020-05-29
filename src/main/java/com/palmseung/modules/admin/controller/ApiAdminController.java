package com.palmseung.modules.admin.controller;

import com.palmseung.infra.properties.AdminProperties;
import com.palmseung.modules.admin.service.AdminService;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.admin.dto.AdminMemberRequestView;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.members.dto.MemberResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAdminController {
    private AdminService adminService;
    private AdminProperties adminProperties;

    public ApiAdminController(AdminService adminService, AdminProperties adminProperties) {
        this.adminService = adminService;
        this.adminProperties = adminProperties;
    }

    @PostMapping("/api/admin")
    public ResponseEntity adminPage(@RequestBody AdminMemberRequestView requestView) {
        if (!adminProperties.canBeAdmin(requestView.getEmail(), requestView.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        AdminMemberResponseView admin = adminService.createAdmin(requestView.toEntity());
        return ResponseEntity
                .ok()
                .body(admin);
    }
}