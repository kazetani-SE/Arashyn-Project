package com.arashi.edu.arashynbe.entity.hub.support;

import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_deck_sync_base")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeckSyncBase {

  @EmbeddedId
  private UserDeckSyncBaseId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userDeckId")
  @JoinColumn(name = "user_deck_id")
  private UserDeck userDeck;
}