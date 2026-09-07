package com.arashi.edu.arashynbe.entity.system.support;

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
public class DeckGrammarId implements Serializable {

  @Column(name = "deck_id")
  private UUID deckId;

  @Column(name = "grammar_id")
  private UUID grammarId;
}