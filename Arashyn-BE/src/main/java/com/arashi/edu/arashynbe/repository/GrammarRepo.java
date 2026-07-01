package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Grammar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GrammarRepo extends JpaRepository<Grammar, UUID> {

  List<Grammar> findByLanguage(String language);

  List<Grammar> findByOwnerId(UUID ownerId);

  List<Grammar> findByIsPublicTrue();

  List<Grammar> findByLanguageAndIsPublicTrue(String language);

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
}