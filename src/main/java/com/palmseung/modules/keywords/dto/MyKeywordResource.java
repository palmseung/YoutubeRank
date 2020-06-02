package com.palmseung.modules.keywords.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MyKeywordResource extends EntityModel {
    public MyKeywordResource(Object content, Link... links) {
        super(content, links);
    }
}
