package com.palmseung.support;

import com.palmseung.member.domain.Member;
import com.palmseung.member.dto.CreateMemberRequestView;
import com.palmseung.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithMemberSecurityContextFactory implements WithSecurityContextFactory<WithMember> {
    private final MemberService memberService;

    @Override
    public SecurityContext createSecurityContext(WithMember withMember) {
        Member member = createMemberForAnnotation(withMember.name());

        UserDetails userDetails = memberService.loadUserByUsername(member.getEmail());
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    private Member createMemberForAnnotation(String name) {
        Member member = Member.builder()
                .name(name)
                .email(name + "@email.com")
                .password("password")
                .build();

        return memberService.create(CreateMemberRequestView.of(member));
    }
}
