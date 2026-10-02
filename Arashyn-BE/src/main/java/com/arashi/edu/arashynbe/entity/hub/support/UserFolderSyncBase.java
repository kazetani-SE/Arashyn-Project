package com.arashi.edu.arashynbe.entity.hub.support;

import com.arashi.edu.arashynbe.entity.hub.UserFolder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_folder_sync_base")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFolderSyncBase {

  @EmbeddedId
  private UserFolderSyncBaseId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userFolderId")
  @JoinColumn(name = "user_folder_id")
  private UserFolder userFolder;
}