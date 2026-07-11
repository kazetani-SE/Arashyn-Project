package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;

import java.util.UUID;

public interface GrammarModifyService {

  void extendGrammar(UUID grammarId, GrammarExtendRequest request);

  void updateGrammar(UUID grammarId, GrammarCreateRequest request);

}