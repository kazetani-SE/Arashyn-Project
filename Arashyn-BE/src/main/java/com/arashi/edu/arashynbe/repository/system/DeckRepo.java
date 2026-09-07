package com.arashi.edu.arashynbe.repository.system;

import com.arashi.edu.arashynbe.entity.system.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeckRepo extends JpaRepository<Deck, UUID> {

  @Modifying
  @Transactional
  @Query("""
      UPDATE Deck d
      SET d.owner = null,
          d.isPublic = false
      WHERE d.id = :deckId
      """)
  int softDelete(UUID deckId);

}