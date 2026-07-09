package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComponentRepo extends JpaRepository<Component, UUID> {

  List<Component> findByGrammarIdOrderByGroupKeyAscOrderAsc(UUID grammarId);

  List<Component> findByFormId(UUID formId);

  boolean existsByGrammarIdAndGroupKey(
          UUID grammarId,
          Short groupKey
  );

  List<Component> findByGrammarIdInAndGroupKeyOrderByGrammarIdAscOrderAsc(
          List<UUID> grammarIds,
          Short groupKey
  );

  @Query("""
    SELECT c.grammar.id
    FROM Component c
    WHERE c.keyword = :keyword
      AND c.order = :order
    """)
  List<UUID> findGrammarIdsByKeywordAndOrder(
          @Param("keyword") String keyword,
          @Param("order") Short order
  );

  @Query("""
    SELECT DISTINCT c.grammar.id
    FROM Component c
    WHERE c.form.id = :formId
      AND c.order = :order
    """)
  List<UUID> findGrammarIdsByFormAndOrder(
          @Param("formId") UUID formId,
          @Param("order") Short order
  );

  List<Component> findByGrammarIdInOrderByGrammarIdAscOrderAsc(
          List<UUID> grammarIds
  );

}