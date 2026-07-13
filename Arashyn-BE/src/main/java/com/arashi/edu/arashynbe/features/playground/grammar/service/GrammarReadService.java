package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarEditResponse;
import java.util.UUID;

public interface GrammarReadService {

  GrammarDetailResponse getDetail(UUID grammarId);

  GrammarEditResponse getEditDetail(UUID grammarId);
}