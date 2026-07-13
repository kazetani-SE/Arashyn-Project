package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarUpdateRequest;

import java.util.UUID;

public interface GrammarModifyService {

  void extendGrammar(UUID grammarId, GrammarExtendRequest request);

  void updateGrammar(GrammarUpdateRequest request);

}