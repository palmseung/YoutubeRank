package com.palmseung.members.dto;

import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.keywords.dto.MyKeywordResponseView;
import com.palmseung.members.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminMemberResponseView {
    private Long id;
    private String email;
    private String name;
    private String password;
    private int keywordCount;
    private List<MyKeywordResponseView> memberKeywords = new ArrayList<>();

    @Builder
    public AdminMemberResponseView(Long id, String email, String name, String password, int keywordCount, List<MyKeywordResponseView> memberKeywords) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.keywordCount = keywordCount;
        this.memberKeywords = memberKeywords;
    }

    public static AdminMemberResponseView of(Member member) {
        return AdminMemberResponseView.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .keywordCount(member.getMyKeywords().size())
                .memberKeywords(convertToView(member.getMyKeywords()))
                .build();
    }

    private static List<MyKeywordResponseView> convertToView(List<MyKeyword> myKeywords) {
        return MyKeywordResponseView.listOf(myKeywords);
    }
}
