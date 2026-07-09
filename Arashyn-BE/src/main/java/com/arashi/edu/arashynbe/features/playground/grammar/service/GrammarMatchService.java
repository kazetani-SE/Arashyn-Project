package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;
import jakarta.validation.Valid;

public interface GrammarIdentifyService {

  ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  );

  GrammarSimilarResponse findSimilarGrammar(
          @Valid GrammarCreateRequest request
  );
}