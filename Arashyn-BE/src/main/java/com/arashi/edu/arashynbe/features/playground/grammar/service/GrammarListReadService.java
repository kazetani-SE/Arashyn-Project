package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import org.springframework.data.domain.Pageable;

public interface GrammarListReadService {

  GrammarListResponse getPublicGrammars(GrammarListRequest request, Pageable pageable);

}