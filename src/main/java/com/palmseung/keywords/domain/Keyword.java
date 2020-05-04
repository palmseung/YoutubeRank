package com.palmseung.keywords.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "keyword_id")
    private Long id;

    @Column(nullable = false)
    private String keyword;

    @OneToMany(mappedBy = "keyword")
    private List<MyKeyword> myKeywords = new ArrayList<>();

    @Builder
    public Keyword(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword1 = (Keyword) o;
        return Objects.equals(id, keyword1.id) &&
                Objects.equals(keyword, keyword1.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyword);
    }
}