package com.arashi.edu.arashynbe.repository.hub;

import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDeckRepo extends JpaRepository<UserDeck, UUID> {

  boolean existsByDeckId(UUID deckId);

  Optional<UserDeck> findByIdAndUserId(UUID id, UUID userId);

  Optional<UserDeck> findFirstByDeckIdAndUserId(
          UUID deckId,
          UUID userId
  );

  List<UserDeck> findAllByUserId(UUID userId);

  boolean existsByIdAndUserIdAndUserFoldersId( UUID id, UUID userId, UUID userFolderId );

  boolean existsByIdAndUserIdAndUserGrammarsId(UUID id, UUID userId, UUID userGrammarsId);

  List<UserDeck> findAllByUserFoldersIdAndUserId(UUID folderId, UUID userId);

  @Query("""
      select d from UserDeck d
      where d.user.id = :userId
        and d.userFolders is empty
      """)
  List<UserDeck> findAllRootByUserId(UUID userId);
}