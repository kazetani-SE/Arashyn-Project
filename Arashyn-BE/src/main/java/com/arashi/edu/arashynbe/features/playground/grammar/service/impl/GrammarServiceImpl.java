package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarEditResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;

import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarCreateService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarDeleteService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarMatchService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarModifyService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarReadService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;

import com.arashi.edu.arashynbe.shared.enums.GrammarSortBy;
import com.arashi.edu.arashynbe.shared.enums.SortDirection;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {

  private final GrammarCreateService grammarCreateService;
  private final GrammarReadService grammarReadService;
  private final GrammarMatchService grammarMatchService;
  private final GrammarDeleteService grammarDeleteService;
  private final GrammarModifyService  grammarModifyService;
  private final GrammarListReadServiceImpl grammarListReadService;

  private final int PAGE_SIZE = 20;

  @Override
  public String createNewGrammar(GrammarCreateRequest request) {
    return grammarCreateService.createNewGrammar(request);
  }

  @Override
  @Transactional(readOnly = true)
  public GrammarDetailResponse getDetail(UUID grammarId) {
    return grammarReadService.getDetail(grammarId);
  }

  @Override
  @Transactional(readOnly = true)
  public GrammarListResponse getPublicGrammars(
          GrammarListRequest request,
          Integer page
  ) {

    GrammarSortBy sortBy = request.sortBy() == null
            ? GrammarSortBy.TITLE
            : request.sortBy();

    SortDirection direction = request.direction() == null
            ? SortDirection.DESC
            : request.direction();

    Pageable pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by(
                    direction.toSpringDirection(),
                    sortBy.getField()
            )
    );

    return grammarListReadService.getPublicGrammars(request, pageable);
  }

  @Override
  public ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  ){
    return grammarMatchService.findExistingGrammar(request);
  }

  @Override
  public GrammarSimilarResponse findSimilarGrammar(@Valid GrammarCreateRequest request) {
    return grammarMatchService.findSimilarGrammar(request);
  }

  @Override
  public void deleteGrammar(UUID request) {
    grammarDeleteService.deleteGrammar(request);
  }

  @Override
  public void restoreGrammar(UUID request) {
    grammarDeleteService.restoreGrammar(request);
  }

  @Override
  public void extendGrammar(UUID grammarId, GrammarExtendRequest request) {
    grammarModifyService.extendGrammar(grammarId, request);
  }

  @Override
  @Transactional(readOnly = true)
  public GrammarEditResponse getEditDetail(UUID grammarId) {
    return grammarReadService.getEditDetail(grammarId);
  }

  @Override
  public void updateGrammar(GrammarUpdateRequest request) {
    grammarModifyService.updateGrammar(request);
  }

  @Override
  public GrammarListResponse getGrammars(Pageable pageable) {
    return grammarListReadService.getGrammars(pageable);
  }

  @Override
  public GrammarListResponse search(String query, List<String> filters, Pageable pageable) {
    return grammarListReadService.search(query, filters, pageable);
  }

}