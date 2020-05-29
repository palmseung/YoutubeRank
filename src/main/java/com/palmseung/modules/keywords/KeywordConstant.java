package com.palmseung.modules.keywords;

import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.domain.MyKeyword;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.palmseung.modules.members.MemberConstant.TEST_MEMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordConstant {
    public static final String BASE_URI_KEYWORD_API = "/api/keywords";

    public static final Keyword TEST_KEYWORD = Keyword.builder()
            .id(1L)
            .keyword("queendom")
            .build();

    public static final Keyword TEST_KEYWORD_2 = Keyword.builder()
            .id(2L)
            .keyword("(g)idle")
            .build();

    public static final Keyword TEST_KEYWORD_3 = Keyword.builder()
            .id(3L)
            .keyword("GOT7")
            .build();

    public static final MyKeyword TEST_MY_KEYWORD = MyKeyword.builder()
            .id(1L)
            .member(TEST_MEMBER)
            .keyword(TEST_KEYWORD)
            .build();

    public static final MyKeyword TEST_MY_KEYWORD_2 = MyKeyword.builder()
            .id(2L)
            .member(TEST_MEMBER)
            .keyword(TEST_KEYWORD_2)
            .build();

    public static final MyKeyword TEST_MY_KEYWORD_3 = MyKeyword.builder()
            .id(3L)
            .member(TEST_MEMBER)
            .keyword(TEST_KEYWORD_3)
            .build();
}
