package com.palmseung.keyword.dto;

import com.palmseung.keyword.domain.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@Getter
public class KeywordResponseView {
    private Long id;
    private String keyword;

    @Builder
    public KeywordResponseView(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static List<KeywordResponseView> listOf(List<Keyword> keywords) {
        return keywords.stream()
                .map(k -> KeywordResponseView.builder()
                        .id(k.getId())
                        .keyword(k.getKeyword())
                        .build())
                .collect(toList());
    }
}
