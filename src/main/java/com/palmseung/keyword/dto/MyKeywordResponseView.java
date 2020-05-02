package com.palmseung.keyword.dto;

import com.palmseung.keyword.domain.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@Getter
public class MyKeywordResponseView {
    private Long id;
    private String keyword;

    @Builder
    public MyKeywordResponseView(Long myKeywordId, String keyword) {
        this.id = myKeywordId;
        this.keyword = keyword;
    }

    public static List<MyKeywordResponseView> listOf(List<Keyword> keywords) {
        return keywords.stream()
                .map(k -> MyKeywordResponseView.builder()
                        .myKeywordId(k.getId())
                        .keyword(k.getKeyword())
                        .build())
                .collect(toList());
    }
}
