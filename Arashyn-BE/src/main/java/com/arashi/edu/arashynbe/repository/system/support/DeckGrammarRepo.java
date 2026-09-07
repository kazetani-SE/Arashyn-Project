package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.support.DeckGrammar;
import com.arashi.edu.arashynbe.entity.system.support.DeckGrammarId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeckGrammarRepo extends JpaRepository<DeckGrammar, DeckGrammarId> {

  List<DeckGrammar> findByIdDeckId(UUID deckId);

  void deleteByIdDeckId(UUID deckId);

}