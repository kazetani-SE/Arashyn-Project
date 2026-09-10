package com.arashi.edu.arashynbe.features.system.grammar.service.impl;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.component.serivce.ComponentService;
import com.arashi.edu.arashynbe.features.system.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.system.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarCreateResponse;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarCreateService;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarMatchService;
import com.arashi.edu.arashynbe.features.system.meaning.service.MeaningService;
import com.arashi.edu.arashynbe.features.system.note.service.NoteService;
import com.arashi.edu.arashynbe.repository.auth.AccountRepo;
import com.arashi.edu.arashynbe.repository.system.GrammarRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.enums.Language;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrammarCreateServiceImpl implements GrammarCreateService {

  private final AccountRepo accountRepo;
  private final GrammarRepo grammarRepo;
  private final ComponentService componentService;
  private final MeaningService meaningService;
  private final NoteService noteService;
  private final SystemFilterService systemFilterService;
  private final GrammarMatchService grammarMatchService;

  private final CurrentAccountProvider currentAccountProvider;

  @Override
  @Transactional
  public GrammarCreateResponse createNewGrammar(@Valid GrammarCreateRequest request) {

    if (grammarMatchService.findExistingGrammar(request).grammarId().isPresent()) {
      throw new ApiException(ErrorCode.GRAMMAR_ALREADY_EXISTS);
    }

    Account owner = currentAccountProvider.get();

    Grammar grammar = createGrammar(
            request.title(),
            request.language(),
            request.isPublic(),
            owner
    );

    systemFilterService.assignFilters(
            grammar,
            new AssignFilterRequest(request.filterIds())
    );

    for (GrammarCreateRequest.Group group : request.groups()) {

      componentService.createComponents(
              grammar,
              group.groupKey(),
              group.components()
      );

      meaningService.createMany(
              grammar,
              group.groupKey(),
              group.meanings()
      );
    }

    noteService.createMany(
            grammar,
            request.notes()
    );

    return new GrammarCreateResponse(grammar.getId());
  }

  private Grammar createGrammar(
          String title,
          Language language,
          boolean isPublic,
          Account owner
  ) {

    Grammar grammar = Grammar.builder()
            .title(title.trim())
            .language(language.name())
            .owner(owner)
            .isPublic(isPublic)
            .build();

    return grammarRepo.save(grammar);
  }
}