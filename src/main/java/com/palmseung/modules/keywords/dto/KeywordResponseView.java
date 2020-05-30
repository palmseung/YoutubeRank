package com.palmseung.modules.keywords.dto;

import com.palmseung.modules.keywords.domain.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordResponseView {
    private Long id;
    private String keyword;

    public KeywordResponseView(String keyword) {
        this.keyword = keyword;
    }

    @Builder
    public KeywordResponseView(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static KeywordResponseView of(Keyword keyword) {
        return KeywordResponseView.builder()
                .id(keyword.getId())
                .keyword(keyword.getKeyword())
                .build();
    }
}