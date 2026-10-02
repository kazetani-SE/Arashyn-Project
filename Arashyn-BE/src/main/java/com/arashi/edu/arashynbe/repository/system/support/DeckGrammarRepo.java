package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.support.DeckGrammar;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammarId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DeckGrammarRepo extends JpaRepository<DeckGrammar, DeckGrammarId> {

  List<DeckGrammar> findByIdDeckId(UUID deckId);

  @Query("select dg.id.grammarId from DeckGrammar dg where dg.id.deckId = :deckId")
  Set<UUID> findGrammarIdsByDeckId(UUID deckId);


  @Modifying
  @Query("delete from DeckGrammar dg where dg.id.deckId = :deckId and dg.id.grammarId in :grammarIds")
  void deleteByIdDeckIdAndIdGrammarIdIn(UUID deckId, Set<UUID> grammarIds);
}