package com.arashi.edu.arashynbe.features.system.grammar.service;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GrammarListReadService {

  GrammarListResponse getPublicGrammars(GrammarListRequest request, Pageable pageable);

  GrammarListResponse getGrammars(Pageable pageable  );

  GrammarListResponse search(
          String query,
          List<String> filters,
          Pageable pageable
  );

  GrammarListResponse getByDeckId(UUID deckId);
}