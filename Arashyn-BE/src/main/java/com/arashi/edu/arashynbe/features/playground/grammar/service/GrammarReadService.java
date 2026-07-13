package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarEditResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GrammarReadService {

  GrammarDetailResponse getDetail(UUID grammarId);

  GrammarListResponse getPublicGrammars(
          GrammarListRequest request,
          Pageable pageable
  );

  GrammarEditResponse getEditDetail(UUID grammarId);
}