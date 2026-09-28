package com.arashi.edu.arashynbe.repository.system;

import com.arashi.edu.arashynbe.entity.system.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepo extends JpaRepository<Folder, UUID> {

  List<Folder> findAllByIsPublicTrue();

  Optional<Folder> findByIdAndOwnerIsNotNull(UUID id);

  @Modifying
  @Transactional
  @Query("""
      UPDATE Folder f
      SET f.owner = null,
          f.isPublic = false
      WHERE f.id = :folderId
      """)
  void softDelete(UUID folderId);

  @Query("""
    select c from Folder c
    join fetch c.owner o
    where c.id in (
        select fh.id.childId from FolderHierarchy fh
        where fh.id.parentId = :folderId
    )
    and (c.isPublic = true or o.id = :userId)
    """)
  List<Folder> findVisibleChildren(UUID folderId, UUID userId);

  @Query("""
    select p from Folder p
    join fetch p.owner o
    where p.id in (
        select fh.id.parentId from FolderHierarchy fh
        where fh.id.childId = :folderId
    )
    and (p.isPublic = true or o.id = :userId)
    """)
  List<Folder> findVisibleParents(UUID folderId, UUID userId);
}