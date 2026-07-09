package com.arashi.edu.arashynbe.features.playground.grammar.service;

import java.util.UUID;

public interface GrammarDeleteService {

  void deleteGrammar(UUID request);

  void restoreGrammar(UUID grammarId);

}