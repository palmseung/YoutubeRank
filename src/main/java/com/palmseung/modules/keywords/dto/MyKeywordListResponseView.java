package com.palmseung.modules.keywords.dto;

import com.palmseung.modules.keywords.domain.MyKeyword;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class MyKeywordListResponseView {
    private List<MyKeywordResponseView> myKeywords = new ArrayList<>();

    public MyKeywordListResponseView() {
    }

    public MyKeywordListResponseView(List<MyKeyword> myKeywords) {
        this.myKeywords = myKeywords.stream()
                .map(m -> MyKeywordResponseView.of(m))
                .collect(toList());
    }

    public static MyKeywordListResponseView of(List<MyKeyword> myKeywords) {
        return new MyKeywordListResponseView(myKeywords);
    }
}