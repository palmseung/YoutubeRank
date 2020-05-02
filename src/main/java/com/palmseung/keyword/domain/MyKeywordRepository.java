package com.palmseung.keyword.domain;

import com.palmseung.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MyKeywordRepository extends JpaRepository<MyKeyword, Long> {

    List<MyKeyword> findAllByMember(Member member);
}
