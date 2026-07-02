package com.arashi.edu.arashynbe.features.playground.meaning.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.Meaning;
import com.arashi.edu.arashynbe.features.playground.example.service.ExampleService;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateBase;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeaningServiceImpl implements MeaningService {

  private final MeaningRepo meaningRepo;
  private final GrammarRepo grammarRepo;
  private final ComponentRepo componentRepo;
  private final AccountRepo accountRepo;
  private final ExampleService exampleService;

  @Override
  public void create(
          UUID grammarId,
          MeaningCreateRequest request
  ) {

    UUID userId = CurrentUser.getId();

    Grammar grammar = grammarRepo.findById(grammarId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    if (!grammar.getIsPublic()
            && !grammar.getOwner().getId().equals(userId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));

    List<Meaning> entities = new ArrayList<>();

    for (MeaningCreateRequest.MeaningCreate dto : request.meanings()) {

      if (!componentRepo.existsByGrammarIdAndGroupKey(
              grammarId,
              dto.groupKey().shortValue())) {
        throw new ApiException(ErrorCode.INVALID_GROUP_KEY);
      }

      Meaning entity = Meaning.builder()
              .grammar(grammar)
              .owner(owner)
              .content(dto.meaning().content().trim())
              .groupKey(dto.groupKey().shortValue())
              .isPublic(grammar.getIsPublic())
              .build();

      entities.add(entity);
    }

    List<Meaning> saved = meaningRepo.saveAll(entities);

    for (int i = 0; i < saved.size(); i++) {
      exampleService.createMany(
              saved.get(i),
              request.meanings().get(i).meaning().examples()
      );
    }
  }

  @Override
  public void createMany(
          Grammar grammar,
          Integer groupKey,
          List<MeaningCreateBase> meanings
  ) {

    if (meanings == null || meanings.isEmpty()) {
      return;
    }

    List<Meaning> entities = new ArrayList<>();

    for (MeaningCreateBase dto : meanings) {

      Meaning entity = Meaning.builder()
              .grammar(grammar)
              .owner(grammar.getOwner())
              .content(dto.content().trim())
              .groupKey(groupKey.shortValue())
              .isPublic(grammar.getIsPublic())
              .build();

      entities.add(entity);
    }

    List<Meaning> saved = meaningRepo.saveAll(entities);

    for (int i = 0; i < saved.size(); i++) {
      exampleService.createMany(
              saved.get(i),
              meanings.get(i).examples()
      );
    }
  }
}