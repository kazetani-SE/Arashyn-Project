package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.support.FolderDeck;
import com.arashi.edu.arashynbe.entity.system.support.FolderDeckId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FolderDeckRepo extends JpaRepository<FolderDeck, FolderDeckId> {

  List<FolderDeck> findByIdDeckId(UUID deckId);

  List<FolderDeck> findByFolderId(UUID folderId);

  @Query("""
    select fd from FolderDeck fd
    join fetch fd.deck d
    left join fetch d.owner
    where fd.id.folderId = :folderId
    """)
  List<FolderDeck> findWithDeckByFolderId(UUID folderId);

  boolean existsByIdFolderIdAndIdDeckId(UUID folderId, UUID deckId);

  @Modifying(flushAutomatically = true)
  @Query("""
    delete from FolderDeck fd 
    where fd.id.folderId = :folderId 
    and fd.id.deckId = :deckId
    """)
  int deleteLink(UUID folderId, UUID deckId);

  @Query("select fd.id.deckId from FolderDeck fd where fd.id.folderId = :folderId")
  Set<UUID> findDeckIdsByFolderId(UUID folderId);
}