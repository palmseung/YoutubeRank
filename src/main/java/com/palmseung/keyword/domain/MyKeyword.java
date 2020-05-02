package com.palmseung.keyword.domain;

import com.palmseung.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
public class MyKeyword implements Serializable {
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
