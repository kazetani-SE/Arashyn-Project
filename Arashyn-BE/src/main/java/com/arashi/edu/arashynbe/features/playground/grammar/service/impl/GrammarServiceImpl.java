package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrammarServiceImpl implements GrammarService {


  private final AccountRepo accountRepo;
  private final GrammarRepo grammarRepo;

  @Override
  public String createNewGrammar(GrammarCreateRequest request) {
    UUID userId = CurrentUser.getId();

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    Grammar grammar = Grammar.builder()
            .title(request.title())
            .language(request.language().name())
            .owner(owner)
            .isPublic(request.isPublic())
            .build();

    Grammar saved = grammarRepo.save(grammar);

    return saved.getId().toString();
  }
}