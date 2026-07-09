package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.serivce.ComponentService;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarReadService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarMatchService;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import com.arashi.edu.arashynbe.features.playground.note.service.NoteService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.shared.enums.GrammarSortBy;
import com.arashi.edu.arashynbe.shared.enums.SortDirection;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {

  private final AccountRepo accountRepo;
  private final GrammarRepo grammarRepo;
  private final ComponentService componentService;
  private final MeaningService meaningService;
  private final NoteService noteService;
  private final SystemFilterService systemFilterService;
  private final GrammarReadService readService;
  private final GrammarMatchService grammarMatchService;

  private final int PAGE_SIZE = 20;

  @Override
  @Transactional
  public String createNewGrammar(GrammarCreateRequest request) {

    var userId = CurrentUser.getId();

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));

    Grammar grammar = Grammar.builder()
            .title(request.title().trim())
            .language(request.language().name())
            .owner(owner)
            .isPublic(request.isPublic())
            .build();

    Grammar savedGrammar = grammarRepo.save(grammar);

    systemFilterService.assignFilters(
            savedGrammar,
            new AssignFilterRequest(request.filterIds())
    );

    for (GrammarCreateRequest.Group group : request.groups()) {

      componentService.createComponents(
              savedGrammar,
              group.groupKey(),
              group.components()
      );

      meaningService.createMany(
              savedGrammar,
              group.groupKey(),
              group.meanings()
      );
    }

    noteService.createMany(
            savedGrammar,
            request.notes()
    );

    return savedGrammar.getId().toString();
  }

  @Override
  @Transactional(readOnly = true)
  public GrammarDetailResponse getDetail(UUID grammarId) {
    return readService.getDetail(grammarId);
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

    return readService.getPublicGrammars(request, pageable);
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

}