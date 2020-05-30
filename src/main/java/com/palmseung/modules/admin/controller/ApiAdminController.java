package com.palmseung.modules.admin.controller;

import com.palmseung.infra.properties.AdminProperties;
import com.palmseung.modules.admin.dto.AdminMemberRequestView;
import com.palmseung.modules.admin.dto.AdminMemberResponseView;
import com.palmseung.modules.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.palmseung.modules.admin.AdminConstant.BASE_URI_ADMIN;

@RestController
@RequestMapping(value = BASE_URI_ADMIN)
public class ApiAdminController {
    private AdminService adminService;
    private AdminProperties adminProperties;

    public ApiAdminController(AdminService adminService, AdminProperties adminProperties) {
        this.adminService = adminService;
        this.adminProperties = adminProperties;
    }

    @PostMapping
    public ResponseEntity createAdmin(@RequestBody AdminMemberRequestView requestView) {
        if (!adminProperties.canBeAdmin(requestView.getEmail(), requestView.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        AdminMemberResponseView admin = adminService.createAdmin(requestView.toEntity());
        return ResponseEntity
                .created(URI.create("/api/admin/" + admin.getId()))
                .body(admin);
    }
}