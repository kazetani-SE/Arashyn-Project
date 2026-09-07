package com.arashi.edu.arashynbe.entity.system.support;

import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.Folder;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "folder_deck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDeck {

  @EmbeddedId
  private FolderDeckId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("folderId")
  @JoinColumn(name = "folder_id")
  private Folder folder;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("deckId")
  @JoinColumn(name = "deck_id")
  private Deck deck;

}