package com.palmseung.modules.members.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MyInfoResponseResource extends EntityModel<MyInfoResponseView> {
    public MyInfoResponseResource(MyInfoResponseView content, Link... links) {
        super(content, links);
    }
}
