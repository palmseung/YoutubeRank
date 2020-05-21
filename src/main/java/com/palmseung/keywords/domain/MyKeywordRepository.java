package com.palmseung.keywords.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface MyKeywordRepository extends JpaRepository<MyKeyword, Long> {
    List<MyKeyword> findAllByMemberId(Long memberId);

    Optional<MyKeyword> findByKeywordIdAndMemberId(Long memberId, Long keywordId);
}