package com.palmseung.modules.keywords.dto;

import com.palmseung.modules.keywords.domain.MyKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@Getter
@Setter
public class MyKeywordResponseView implements Serializable {
    private Long id;
    private String email;
    private String keyword;

    @Builder
    public MyKeywordResponseView(Long id, String email, String keyword) {
        this.id = id;
        this.email = email;
        this.keyword = keyword;
    }

    public static MyKeywordResponseView of(MyKeyword myKeyword) {
        return MyKeywordResponseView.builder()
                .id(myKeyword.getId())
                .email(myKeyword.getMember().getEmail())
                .keyword(myKeyword.getStringKeyword())
                .build();
    }

    public static List<MyKeywordResponseView> listOf(List<MyKeyword> myKeywords) {
        return myKeywords.stream()
                .map(MyKeywordResponseView::of)
                .collect(toList());
    }
}