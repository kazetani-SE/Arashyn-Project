package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GrammarListReadService {

  GrammarListResponse getPublicGrammars(GrammarListRequest request, Pageable pageable);

  GrammarListResponse getGrammars(Pageable pageable  );

  GrammarListResponse search(
          String query,
          List<String> filters,
          Pageable pageable
  );
}