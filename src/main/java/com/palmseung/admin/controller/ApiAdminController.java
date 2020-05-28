package com.palmseung.admin.controller;

import com.palmseung.admin.support.AdminProperties;
import com.palmseung.admin.service.AdminService;
import com.palmseung.members.domain.Member;
import com.palmseung.members.dto.AdminMemberRequestView;
import com.palmseung.members.dto.AdminMemberResponseView;
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

        Member admin = adminService.createAdmin(requestView.toEntity());
        return ResponseEntity
                .ok()
                .body(AdminMemberResponseView.of(admin));
    }
}
