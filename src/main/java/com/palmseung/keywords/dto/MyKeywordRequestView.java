package com.palmseung.keywords.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyKeywordRequestView {
    private String keyword;

    @Builder
    public MyKeywordRequestView(String keyword) {
        this.keyword = keyword;
    }
}
