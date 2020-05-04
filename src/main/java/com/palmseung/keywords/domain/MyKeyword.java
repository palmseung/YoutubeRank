package com.palmseung.keywords.domain;

import com.palmseung.members.domain.Member;
import com.palmseung.support.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MyKeyword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memberkeyword_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public MyKeyword(Long id, Member member, Keyword keyword) {
        this.id = id;
        this.member = member;
        this.keyword = keyword;
    }
}