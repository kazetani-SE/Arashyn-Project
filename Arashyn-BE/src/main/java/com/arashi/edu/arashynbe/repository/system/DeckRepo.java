package com.arashi.edu.arashynbe.repository.system;

import com.arashi.edu.arashynbe.entity.system.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeckRepo extends JpaRepository<Deck, UUID> {

  @Modifying
  @Query("""
      UPDATE Deck d
      SET d.owner = null,
          d.isPublic = false
      WHERE d.id = :deckId
      """)
  void softDelete(UUID deckId);

  @Query("""
      select d from Deck d
      join fetch d.owner
      where d.isPublic = true
      """)
  List<Deck> findAllPublicWithOwner();
}