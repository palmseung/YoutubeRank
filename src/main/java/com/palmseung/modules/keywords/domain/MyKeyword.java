package com.palmseung.modules.keywords.domain;

import com.palmseung.modules.members.domain.Member;
import com.palmseung.infra.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MyKeyword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getStringKeyword(){
        return this.keyword.getKeyword();
    }
}