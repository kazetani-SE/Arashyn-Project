package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.serivce.ComponentService;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {

  private final AccountRepo accountRepo;
  private final GrammarRepo grammarRepo;
  private final ComponentService componentService;
  private final MeaningService meaningService;

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

    return savedGrammar.getId().toString();
  }
}