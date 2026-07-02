package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.SystemFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemFilterRepo extends JpaRepository<SystemFilter, UUID> {

  Optional<SystemFilter> findByNameAndLanguage(
          String name,
          String language
  );

  @Query(value = """
    SELECT sf.*
    FROM system_filter sf
    JOIN grammar_filter gf
        ON sf.id = gf.filter_id
    WHERE gf.grammar_id = :grammarId
    ORDER BY sf.name
    """, nativeQuery = true)
  List<SystemFilter> findAllByGrammarId(UUID grammarId);
}