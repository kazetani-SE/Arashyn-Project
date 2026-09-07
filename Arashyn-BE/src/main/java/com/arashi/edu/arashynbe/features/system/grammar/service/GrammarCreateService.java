package com.arashi.edu.arashynbe.features.system.grammar.service;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarCreateResponse;

public interface GrammarCreateService {

  GrammarCreateResponse createNewGrammar(GrammarCreateRequest request);
}