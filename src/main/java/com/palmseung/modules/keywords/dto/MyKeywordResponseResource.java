package com.palmseung.modules.keywords.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MyKeywordResponseResource extends EntityModel<MyKeywordResponseView> {
    public MyKeywordResponseResource(MyKeywordResponseView content, Link... links) {
        super(content, links);
    }
}
