package com.arashi.edu.arashynbe.repository.hub;

import com.arashi.edu.arashynbe.entity.hub.UserFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFolderRepo extends JpaRepository<UserFolder, UUID> {

  List<UserFolder> findAllByUserId(UUID userId);

  Optional<UserFolder> findByIdAndUserId(UUID id, UUID userId);

  boolean existsByFolderId(UUID folderId);

  Optional<UserFolder> findFirstByFolderIdAndUserId(UUID folderId, UUID userId);

  List<UserFolder> findAllByChildrenId(UUID childId);

  boolean existsByChildrenId(UUID childId);

  @Query("""
        select f from UserFolder f
        where f.user.id = :userId
          and not exists (
              select 1 from UserFolder p join p.children c where c.id = f.id
          )
        """)
  List<UserFolder> findAllRootByUserId(UUID userId);
}