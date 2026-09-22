package com.arashi.edu.arashynbe.features.system.grammar.service;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarCreateResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarEditResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GrammarService {

  GrammarCreateResponse createNewGrammar(GrammarCreateRequest request);

  void createMultipleGrammar(GrammarCreateMultipleRequest request);

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

  GrammarListResponse getGrammars(Language language, Pageable pageable  );

  GrammarListResponse search(
          String query,
          List<String> filters,
          List<String> forms,
          boolean isKeyword,
          Language language,
          Pageable pageable
  );
}