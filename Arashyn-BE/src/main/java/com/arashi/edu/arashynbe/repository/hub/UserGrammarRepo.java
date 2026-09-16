package com.arashi.edu.arashynbe.repository.hub;

import com.arashi.edu.arashynbe.entity.hub.UserGrammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGrammarRepo extends JpaRepository<UserGrammar, UUID> {

  Optional<UserGrammar> findByIdAndUserId(UUID id, UUID userId);

  Optional<UserGrammar> findFirstByGrammarIdAndUserId(UUID grammarId, UUID userId);

  List<UserGrammar> findAllByUserId(UUID userId);

  @Query("""
    SELECT ug FROM UserDeck ud
    JOIN ud.userGrammars ug
    WHERE ud.id = :deckId AND ud.user.id = :userId
    """)
  List<UserGrammar> findUserGrammarsByDeckIdAndUserId(UUID deckId, UUID userId);

  @Query("""
    SELECT ug.id FROM UserGrammar ug
    WHERE ug.user.id = :userId
    AND NOT EXISTS (
      SELECT 1 FROM UserDeck ud
      JOIN ud.userGrammars udg
      WHERE udg.id = ug.id
    )
    """)
  List<UUID> findRedundantGrammarIds(UUID userId);
}