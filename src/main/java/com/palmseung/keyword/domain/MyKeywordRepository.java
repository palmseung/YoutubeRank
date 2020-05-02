package com.palmseung.keyword.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyKeywordRepository extends JpaRepository<MyKeyword, Long> {
}
