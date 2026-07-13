package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NoteRepo extends JpaRepository<Note, UUID> {

  List<Note> findAllByGrammarIdOrderByGroupKeyAsc(UUID grammarId);

  List<Note> findByGrammarIdAndOwnerIdOrderByGroupKeyAsc(
          UUID grammarId,
          UUID ownerId
  );

  void deleteAllByGrammarId(UUID grammarId);

  @Query("""
    SELECT n
    FROM Note n
    WHERE n.grammar.id = :grammarId
      AND n.owner.id = :ownerId
    ORDER BY n.groupKey
    """)
  List<Note> findAllByGrammarIdAndOwnerId(
          UUID grammarId,
          UUID ownerId
  );

  @Modifying
  @Query("""
      update Note n
         set n.grammar.id = :newGrammarId
       where n.grammar.id = :oldGrammarId
         and (
              n.owner is null
              or n.owner.id <> :creatorId
         )
      """)
  int updateGrammarReferenceExceptCreator(
          @Param("oldGrammarId") UUID oldGrammarId,
          @Param("newGrammarId") UUID newGrammarId,
          @Param("creatorId") UUID creatorId
  );


}