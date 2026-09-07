package com.arashi.edu.arashynbe.entity.system.support;

import com.arashi.edu.arashynbe.entity.system.Folder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "folder_hierarchy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderHierarchy {

  @EmbeddedId
  private FolderHierarchyId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("parentId")
  @JoinColumn(name = "parent_id")
  private Folder parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("childId")
  @JoinColumn(name = "child_id")
  private Folder child;
}