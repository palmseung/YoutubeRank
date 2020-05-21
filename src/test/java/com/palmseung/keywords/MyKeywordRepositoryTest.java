package com.palmseung.keywords;

import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.KeywordRepository;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.domain.MyKeywordRepository;
import com.palmseung.members.domain.Member;
import com.palmseung.members.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.palmseung.keywords.KeywordConstant.TEST_KEYWORD;
import static com.palmseung.members.MemberConstant.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MyKeywordRepositoryTest {
    @Autowired
    private MyKeywordRepository myKeywordRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByKeywordIdAndMemberId() {
        //given
        String keyword = "queendom";
        String email = "email@email.com";
        saveMyKeyword(saveMember(email), saveKeyword(keyword));

        //when
        Optional<MyKeyword> myKeyword
                = myKeywordRepository.findByKeywordIdAndMemberId(TEST_KEYWORD.getId(), TEST_MEMBER.getId());

        //then
        assertThat(myKeyword.isPresent()).isTrue();

        MyKeyword foundMyKeyword = myKeyword.get();
        assertThat(foundMyKeyword.getStringKeyword()).isEqualTo(keyword);
        assertThat(foundMyKeyword.getMember().getEmail()).isEqualTo(email);
    }

    private Keyword saveKeyword(String keyword) {
        return keywordRepository.save(Keyword.of(keyword));
    }

    private Member saveMember(String email) {
        return memberRepository.save(Member.builder()
                .name("user")
                .password("password")
                .email(email)
                .roles(Arrays.asList("ROLE_USER"))
                .build());
    }

    private MyKeyword saveMyKeyword(Member member, Keyword keyword) {
        MyKeyword myKeyword = MyKeyword.builder()
                .member(member)
                .keyword(keyword)
                .build();

        return myKeywordRepository.save(myKeyword);
    }
}