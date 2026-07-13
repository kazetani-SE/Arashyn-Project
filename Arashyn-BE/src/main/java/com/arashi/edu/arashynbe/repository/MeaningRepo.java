package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Meaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeaningRepo extends JpaRepository<Meaning, UUID> {

  List<Meaning> findByGrammarIdOrderByGroupKeyAsc(UUID grammarId);

  List<Meaning> findByGrammarIdAndOwnerIdOrderByGroupKeyAsc(UUID grammarId, UUID ownerId);

  List<Meaning> findByGrammarIdInAndGroupKeyOrderByGrammarIdAsc(
          List<UUID> grammarIds,
          Short groupKey
  );

  @Query("""
    SELECT m
    FROM Meaning m
    WHERE m.grammar.id = :grammarId
      AND m.owner.id = :ownerId
    ORDER BY m.groupKey
    """)
  List<Meaning> findAllByGrammarIdAndOwnerId(
          UUID grammarId,
          UUID ownerId
  );

  @Modifying
  @Query("""
    update Meaning m
       set m.grammar.id = :newGrammarId
     where m.grammar.id = :oldGrammarId
       and (
            m.owner is null
            or m.owner.id <> :creatorId
       )
    """)
  int updateGrammarReferenceExceptCreator(
          @Param("oldGrammarId") UUID oldGrammarId,
          @Param("newGrammarId") UUID newGrammarId,
          @Param("creatorId") UUID creatorId
  );
}