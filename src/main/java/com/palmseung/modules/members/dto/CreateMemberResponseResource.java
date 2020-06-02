package com.palmseung.modules.members.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class CreateMemberResponseResource extends EntityModel<CreateMemberResponseView> {
    public CreateMemberResponseResource(CreateMemberResponseView content, Link... links) {
        super(content, links);
    }
}
