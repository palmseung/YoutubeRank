package com.palmseung.modules.members.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class LoginResponseResource extends EntityModel<LoginResponseView> {
    public LoginResponseResource(LoginResponseView content, Link... links) {
        super(content, links);
    }
}
