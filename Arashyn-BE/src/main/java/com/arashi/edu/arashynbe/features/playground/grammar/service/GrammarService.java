package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarCreateResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarEditResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GrammarService {

  GrammarCreateResponse createNewGrammar(GrammarCreateRequest request);

  GrammarDetailResponse getDetail(UUID grammarId);

  GrammarListResponse getPublicGrammars(
          GrammarListRequest request,
          Integer page
  );

  ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  );

  GrammarSimilarResponse findSimilarGrammar(
          @Valid GrammarCreateRequest request
  );

  void deleteGrammar(UUID request);

  void restoreGrammar(UUID grammarId);

  void extendGrammar(UUID grammarId, GrammarExtendRequest request);

  GrammarEditResponse getEditDetail(UUID grammarId);

  void updateGrammar(GrammarUpdateRequest request);

  GrammarListResponse getGrammars(Pageable pageable  );

  GrammarListResponse search(
          String query,
          List<String> filters,
          Pageable pageable
  );
}