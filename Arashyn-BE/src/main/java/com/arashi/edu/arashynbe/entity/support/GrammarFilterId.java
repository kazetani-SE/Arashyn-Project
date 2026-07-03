package com.arashi.edu.arashynbe.entity.support;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
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
public class GrammarFilterId implements Serializable {

  @Column(name = "grammar_id")
  private UUID grammarId;

  @Column(name = "filter_id")
  private UUID filterId;
}