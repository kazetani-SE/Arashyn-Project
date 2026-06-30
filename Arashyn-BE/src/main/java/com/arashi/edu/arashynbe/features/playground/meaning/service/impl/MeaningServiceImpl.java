package com.arashi.edu.arashynbe.features.playground.meaning.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.Meaning;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.MeaningRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeaningServiceImpl implements MeaningService {

  private final MeaningRepo meaningRepo;
  private final GrammarRepo grammarRepo;
  private final ComponentRepo componentRepo;
  private final AccountRepo accountRepo;

  @Override
  public UUID create(
          UUID grammarId,
          MeaningCreateRequest request
  ) {

    UUID userId = CurrentUser.getId();

    Grammar grammar = grammarRepo.findById(grammarId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    // Public grammar: everyone can contribute meanings.
    // Private grammar: only owner can contribute.
    if (!grammar.getIsPublic()
            && !grammar.getOwner().getId().equals(userId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    if (!componentRepo.existsByGrammarIdAndGroupKey(
            grammarId,
            request.groupKey().shortValue())) {
      throw new ApiException(ErrorCode.INVALID_GROUP_KEY);
    }

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));

    Meaning meaning = Meaning.builder()
            .grammar(grammar)
            .owner(owner)
            .content(request.content().trim())
            .groupKey(request.groupKey().shortValue())
            .isPublic(grammar.getIsPublic())
            .build();

    return meaningRepo.save(meaning).getId();
  }
}