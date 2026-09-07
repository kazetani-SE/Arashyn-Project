package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeckId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderDeckRepo extends JpaRepository<FolderDeck, FolderDeckId> {

  List<FolderDeck> findByIdDeckId(UUID deckId);

  void deleteByIdDeckId(UUID deckId);

}