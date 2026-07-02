package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;

import java.util.UUID;

public interface GrammarService {

  String createNewGrammar(GrammarCreateRequest request);

  GrammarDetailResponse getDetail(UUID grammarId);
}