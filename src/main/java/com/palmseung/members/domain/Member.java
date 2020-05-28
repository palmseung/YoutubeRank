package com.palmseung.members.domain;

import com.palmseung.common.support.BaseTimeEntity;
import com.palmseung.keywords.domain.Keyword;
import com.palmseung.keywords.domain.MyKeyword;
import com.palmseung.members.dto.UpdateMemberRequestView;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.palmseung.common.support.Messages.WARNING_MEMBER_UNAUTHORIZED_TO_UPDATE;

@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MyKeyword> myKeywords = new ArrayList<>();

    public Member() {
    }

    @Builder
    public Member(Long id, String email, String name, String password, List<String> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public static Member of(UpdateMemberRequestView requestView) {
        return Member.builder()
                .password(requestView.getNewPassword())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> collect = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return this.roles.contains("ROLE_ADMIN");
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updatePassword(Member loginUser, String newEncodedPassword) {
        if (!matchEmailAndId(loginUser)) {
            throw new IllegalArgumentException(WARNING_MEMBER_UNAUTHORIZED_TO_UPDATE);
        }
        this.password = newEncodedPassword;
    }

    public void update(Member loginUser, Member updatedMember) {
        if (!matchEmailAndId(loginUser)) {
            throw new IllegalArgumentException(WARNING_MEMBER_UNAUTHORIZED_TO_UPDATE);
        }

        this.name = updatedMember.getName();
        this.password = updatedMember.getPassword();
    }

    private boolean matchEmailAndId(Member loginUser) {
        return matchId(loginUser.getId()) && matchEmail(loginUser.getEmail());
    }

    private boolean matchEmail(String email) {
        return this.email.equals(email);
    }

    private boolean matchId(Long id) {
        return this.id.equals(id);
    }

    public List<Keyword> addKeyword(Keyword keyword) {
        myKeywords.add(MyKeyword.builder()
                .member(this)
                .keyword(keyword)
                .build());
        return getKeywords();
    }

    public List<Keyword> getKeywords() {
        return myKeywords.stream()
                .map(MyKeyword::getKeyword)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}