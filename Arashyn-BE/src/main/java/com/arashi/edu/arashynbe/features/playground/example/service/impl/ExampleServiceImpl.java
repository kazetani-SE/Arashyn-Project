package com.arashi.edu.arashynbe.features.playground.example.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Example;
import com.arashi.edu.arashynbe.entity.Meaning;
import com.arashi.edu.arashynbe.features.playground.example.dto.request.ExampleCreateRequest;
import com.arashi.edu.arashynbe.features.playground.example.service.ExampleService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.ExampleRepo;
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
public class ExampleServiceImpl implements ExampleService {

  private final ExampleRepo exampleRepo;
  private final MeaningRepo meaningRepo;
  private final AccountRepo accountRepo;

  @Override
  public UUID create(
          UUID meaningId,
          ExampleCreateRequest request
  ) {

    UUID userId = CurrentUser.getId();

    Meaning meaning = meaningRepo.findById(meaningId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.MEANING_NOT_FOUND));

    if (!meaning.getIsPublic()
            && !meaning.getOwner().getId().equals(userId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));

    boolean isPublic = meaning.getIsPublic()
            && !Boolean.FALSE.equals(request.isPublic());

    Example example = Example.builder()
            .meaning(meaning)
            .owner(owner)
            .sentence(request.sentence().trim())
            .translation(
                    request.translation() == null
                            ? null
                            : request.translation().trim()
            )
            .note(
                    request.note() == null
                            ? null
                            : request.note().trim()
            )
            .groupKey(meaning.getGroupKey())
            .isPublic(isPublic)
            .build();

    return exampleRepo.save(example).getId();
  }

  @Override
  public void createMany(
          Meaning meaning,
          List<ExampleCreateRequest> examples
  ) {

    if (examples == null || examples.isEmpty()) {
      return;
    }

    List<Example> entities = new ArrayList<>();

    for (ExampleCreateRequest dto : examples) {

      if (dto == null
              || dto.sentence() == null
              || dto.sentence().isBlank()) {
        continue;
      }

      boolean isPublic = meaning.getIsPublic()
              && !Boolean.FALSE.equals(dto.isPublic());

      Example example = Example.builder()
              .meaning(meaning)
              .owner(meaning.getOwner())
              .sentence(dto.sentence().trim())
              .translation(
                      dto.translation() == null
                              ? null
                              : dto.translation().trim()
              )
              .note(
                      dto.note() == null
                              ? null
                              : dto.note().trim()
              )
              .groupKey(meaning.getGroupKey())
              .isPublic(isPublic)
              .build();

      entities.add(example);
    }

    if (!entities.isEmpty()) {
      exampleRepo.saveAll(entities);
    }
  }
}