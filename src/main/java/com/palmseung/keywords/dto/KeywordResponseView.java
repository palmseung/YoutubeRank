package com.palmseung.keywords.dto;

import lombok.Data;

@Data
public class KeywordResponseView {
    private String keyword;

    public KeywordResponseView(String keyword) {
        this.keyword = keyword;
    }
}
