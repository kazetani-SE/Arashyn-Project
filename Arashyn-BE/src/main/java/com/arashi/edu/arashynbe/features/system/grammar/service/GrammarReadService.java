package com.arashi.edu.arashynbe.features.system.grammar.service;

import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarEditResponse;
import java.util.UUID;

public interface GrammarReadService {

  GrammarDetailResponse getDetail(UUID grammarId);

  GrammarEditResponse getEditDetail(UUID grammarId);
}