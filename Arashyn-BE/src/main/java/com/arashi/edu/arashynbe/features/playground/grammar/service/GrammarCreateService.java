package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarCreateResponse;

public interface GrammarCreateService {

  GrammarCreateResponse createNewGrammar(GrammarCreateRequest request);
}