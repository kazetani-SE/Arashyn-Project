package com.arashi.edu.arashynbe.repository.hub;

import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDeckRepo extends JpaRepository<UserDeck, UUID> {

  boolean existsByDeckId(UUID deckId);

}