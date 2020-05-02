package com.palmseung.keyword.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KeywordResponseView {
    private Long id;
    private String email;
    private String keyword;

    @Builder
    public KeywordResponseView(Long id, String email, String keyword) {
        this.id = id;
        this.email = email;
        this.keyword = keyword;
    }
}
