package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Grammar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GrammarRepo extends
        JpaRepository<Grammar, UUID>,
        JpaSpecificationExecutor<Grammar> {

  @Modifying
  @Transactional
  @Query(value = """
        INSERT INTO grammar_filter(grammar_id, filter_id)
        VALUES (:grammarId, :filterId)
        ON CONFLICT DO NOTHING
        """, nativeQuery = true)
  void assignFilter(
          UUID grammarId,
          UUID filterId
  );

  @Query("""
    SELECT g.owner.id
    FROM Grammar g
    WHERE g.id = :grammarId
    """)
  Optional<UUID> getOwnerId(UUID grammarId);

  @Modifying
  @Transactional
  @Query("""
    UPDATE Grammar g
    SET g.owner = null,
        g.isPublic = false
    WHERE g.id = :grammarId
    """)
  int softDelete(UUID grammarId);

  @Modifying
  @Transactional
  @Query("""
    UPDATE Grammar g
    SET g.owner.id = :userId,
        g.isPublic = true
    WHERE g.id = :grammarId
      AND g.owner IS NULL
    """)
  int restoreGrammar(
          UUID grammarId,
          UUID userId
  );
}