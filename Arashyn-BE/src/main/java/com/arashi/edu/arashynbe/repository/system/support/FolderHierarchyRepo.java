package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchy;
import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface FolderHierarchyRepo extends JpaRepository<FolderHierarchy,  FolderHierarchyId> {

  boolean existsByIdParentIdAndIdChildId(UUID parentId, UUID childId);

  @Modifying(flushAutomatically = true)
  @Query("delete from FolderHierarchy fh where fh.id.parentId = :parentId and fh.id.childId = :childId")
  int deleteLink(UUID parentId, UUID childId);

  @Query(nativeQuery = true, value = """
      WITH RECURSIVE descendants AS (
          SELECT child_id FROM folder_hierarchy WHERE parent_id = :folderId
          UNION
          SELECT fh.child_id
          FROM folder_hierarchy fh
          JOIN descendants d ON fh.parent_id = d.child_id
      )
      SELECT EXISTS (SELECT 1 FROM descendants WHERE child_id = :candidateId)
      """)
  boolean isDescendant(UUID folderId, UUID candidateId);

  @Query("select fh.id.childId from FolderHierarchy fh where fh.id.parentId = :folderId")
  Set<UUID> findChildFolderIds(UUID folderId);
}