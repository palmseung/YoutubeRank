package com.palmseung.modules.keywords.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.List;

public class MyKeywordListResponseResource extends EntityModel<List<MyKeywordResponseView>> {
    public MyKeywordListResponseResource(List<MyKeywordResponseView> content, Link... links) {
        super(content, links);
    }
}
