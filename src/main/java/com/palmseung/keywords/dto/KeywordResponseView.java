package com.palmseung.keywords.dto;

import lombok.Data;

@Data
public class KeywordResponseView {
    private Long id;
    private String keyword;

    public KeywordResponseView(String keyword) {
        this.keyword = keyword;
    }

    public KeywordResponseView(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }
}
