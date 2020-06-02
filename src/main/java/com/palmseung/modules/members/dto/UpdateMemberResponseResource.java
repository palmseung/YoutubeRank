package com.palmseung.modules.members.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class UpdateMemberResponseResource extends EntityModel<UpdateMemberResponseView> {
    public UpdateMemberResponseResource(UpdateMemberResponseView content, Link... links) {
        super(content, links);
    }
}
