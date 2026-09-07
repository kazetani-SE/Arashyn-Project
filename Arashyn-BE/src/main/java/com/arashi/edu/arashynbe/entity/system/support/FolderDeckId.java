package com.arashi.edu.arashynbe.entity.system.support;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FolderDeckId {

  @Column(name = "folder_id")
  private UUID folderId;

  @Column(name = "deck_id")
  private UUID deckId;

}