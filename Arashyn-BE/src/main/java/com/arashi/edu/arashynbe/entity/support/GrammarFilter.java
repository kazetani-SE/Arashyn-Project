package com.arashi.edu.arashynbe.entity.support;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.SystemFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grammar_filter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrammarFilter {

  @EmbeddedId
  private GrammarFilterId id;

  @MapsId("grammarId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grammar_id")
  private Grammar grammar;

  @MapsId("filterId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "filter_id")
  private SystemFilter filter;
}