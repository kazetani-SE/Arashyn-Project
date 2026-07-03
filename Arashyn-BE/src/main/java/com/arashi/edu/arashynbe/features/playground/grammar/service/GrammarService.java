package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import java.util.UUID;

public interface GrammarService {

  String createNewGrammar(GrammarCreateRequest request);

  GrammarDetailResponse getDetail(UUID grammarId);

  GrammarListResponse getPublicGrammars(
          GrammarListRequest request,
          Integer page
  );
}