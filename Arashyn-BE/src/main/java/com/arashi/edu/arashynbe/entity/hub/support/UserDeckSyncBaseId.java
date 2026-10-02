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
public class UserDeckSyncBaseId implements Serializable {

  @Column(name = "user_deck_id")
  private UUID userDeckId;

  @Column(name = "grammar_id")
  private UUID grammarId;
}