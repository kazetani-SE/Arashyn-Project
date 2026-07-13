package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;

public interface GrammarCreateService {

  String createNewGrammar(GrammarCreateRequest request);
}