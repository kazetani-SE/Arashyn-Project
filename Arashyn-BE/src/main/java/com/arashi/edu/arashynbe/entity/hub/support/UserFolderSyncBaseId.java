package com.arashi.edu.arashynbe.entity.hub.support;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserFolderSyncBaseId implements Serializable {

  @Column(name = "user_folder_id")
  private UUID userFolderId;

  @Column(name = "child_kind", length = 10)
  private String childKind;

  @Column(name = "child_id")
  private UUID childId;
}