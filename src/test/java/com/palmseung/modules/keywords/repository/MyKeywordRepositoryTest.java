package com.palmseung.modules.keywords.repository;

import com.palmseung.modules.keywords.domain.Keyword;
import com.palmseung.modules.keywords.domain.KeywordRepository;
import com.palmseung.modules.keywords.domain.MyKeyword;
import com.palmseung.modules.keywords.domain.MyKeywordRepository;
import com.palmseung.modules.members.domain.Member;
import com.palmseung.modules.members.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Testcontainers
@SpringBootTest
public class MyKeywordRepositoryTest {
    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();
        POSTGRE_SQL_CONTAINER.start();
    }

    @Autowired
    private MyKeywordRepository myKeywordRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void cleanDataBase() {
        myKeywordRepository.deleteAll();
        keywordRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("MyKeyword 생성")
    @Test
    void create() {
        //given
        String keyword = "ONF - we must love";
        String email = "roadToKingdom@email.com";
        Member member = saveMember(email);
        Keyword savedKeyword = saveKeyword(keyword);

        //when
        MyKeyword save = myKeywordRepository.save(buildMyKeyword(member, savedKeyword));

        //then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getMember().getId()).isEqualTo(member.getId());
        assertThat(save.getStringKeyword()).isEqualTo(keyword);
    }

    @DisplayName("MyKeyword 조회")
    @Test
    void findByKeywordIdAndMemberId() {
        //given
        String keyword = "got7";
        String email = "email2@email.com";
        Member savedMember = saveMember(email);
        Keyword savedKeyword = saveKeyword(keyword);
        myKeywordRepository.save(buildMyKeyword(savedMember, savedKeyword));

        //when
        Optional<MyKeyword> myKeyword
                = myKeywordRepository.findByKeywordIdAndMemberId(savedKeyword.getId(), savedMember.getId());

        //then
        assertThat(myKeyword.isPresent()).isTrue();

        MyKeyword foundMyKeyword = myKeyword.get();
        assertThat(foundMyKeyword.getStringKeyword()).isEqualTo(keyword);
        assertThat(foundMyKeyword.getMember().getEmail()).isEqualTo(email);
    }

    @DisplayName("MyKeyword 목록 조회")
    @Test
    void findAllMyKeywordsByMemberId() {
        //given
        String keyword1 = "ONF";
        String keyword2 = "Moscow Moscow";
        String email = "onandoff@email.com";
        Member savedMember = saveMember(email);
        Keyword savedKeyword1 = saveKeyword(keyword1);
        Keyword savedKeyword2 = saveKeyword(keyword2);
        myKeywordRepository.save(buildMyKeyword(savedMember, savedKeyword1));
        myKeywordRepository.save(buildMyKeyword(savedMember, savedKeyword2));

        //when
        List<MyKeyword> allByMemberId = myKeywordRepository.findAllByMemberId(savedMember.getId());

        //then
        assertThat(allByMemberId.size()).isEqualTo(2);
        assertThat(allByMemberId.get(0).getMember()).isEqualTo(savedMember);
        assertThat(allByMemberId.get(0).getStringKeyword()).isEqualTo(keyword1);
        assertThat(allByMemberId.get(1).getMember()).isEqualTo(savedMember);
        assertThat(allByMemberId.get(1).getStringKeyword()).isEqualTo(keyword2);
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

    private MyKeyword buildMyKeyword(Member member, Keyword keyword) {
        return MyKeyword.builder()
                .member(member)
                .keyword(keyword)
                .build();
    }
}