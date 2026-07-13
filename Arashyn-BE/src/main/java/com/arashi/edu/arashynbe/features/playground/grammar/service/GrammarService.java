package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface GrammarService {

  String createNewGrammar(GrammarCreateRequest request);

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

  GrammarEditResponse getUpdateDetail(UUID grammarId);
}