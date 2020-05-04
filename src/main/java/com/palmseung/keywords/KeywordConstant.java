package com.palmseung.keywords;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.MyKeyword;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.palmseung.members.MemberConstant.TEST_MEMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordConstant {
    public static final String BASE_URI_KEYWORD_API = "/api/keywords";

    public static final Keyword TEST_KEYWORD = Keyword.builder()
            .id(1L)
            .keyword("queendom")
            .build();

    public static final MyKeyword TEST_MY_KEYWORD = MyKeyword.builder()
            .id(1L)
            .member(TEST_MEMBER)
            .keyword(TEST_KEYWORD)
            .build();
}
