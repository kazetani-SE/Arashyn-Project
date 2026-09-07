package com.arashi.edu.arashynbe.entity.system.support;

import com.arashi.edu.arashynbe.entity.system.Deck;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deck_grammar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeckGrammar {

  @EmbeddedId
  private DeckGrammarId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("deckId")
  @JoinColumn(name = "deck_id")
  private Deck deck;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("grammarId")
  @JoinColumn(name = "grammar_id")
  private Grammar grammar;
}