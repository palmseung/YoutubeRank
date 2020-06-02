package com.palmseung.modules.keywords.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MyKeywordListResponseResource extends EntityModel<MyKeywordListResponseView> {
    public MyKeywordListResponseResource(MyKeywordListResponseView content, Link... links) {
        super(content, links);
    }
}
