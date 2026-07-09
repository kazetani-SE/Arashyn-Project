package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import jakarta.validation.Valid;

public interface GrammarRefactorService {

  ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  );

}