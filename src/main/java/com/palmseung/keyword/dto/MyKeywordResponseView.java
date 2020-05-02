package com.palmseung.keyword.dto;

import com.palmseung.keyword.domain.MyKeyword;
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
    private String keyword;

    @Builder
    public MyKeywordResponseView(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static MyKeywordResponseView of(MyKeyword myKeyword) {
        return new MyKeywordResponseView(myKeyword.getId(), myKeyword.getKeyword().getKeyword());
    }

    public static List<MyKeywordResponseView> listOf(List<MyKeyword> myKeywords) {
        return myKeywords.stream()
                .map(MyKeywordResponseView::of)
                .collect(toList());
    }
}