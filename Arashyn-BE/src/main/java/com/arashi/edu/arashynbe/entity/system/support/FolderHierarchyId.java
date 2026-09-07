package com.arashi.edu.arashynbe.entity.system.support;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FolderHierarchyId implements Serializable {

  @Column(name = "parent_id")
  private UUID parentId;

  @Column(name = "child_id")
  private UUID childId;
}