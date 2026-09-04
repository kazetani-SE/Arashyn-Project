package com.arashi.edu.arashynbe.features.playground.filter.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.SystemFilter;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.SystemFilterCreateRequest;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.ListSystemFilterResponse;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.SystemFilterRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemFilterServiceImpl implements SystemFilterService {

  private final GrammarRepo grammarRepo;
  private final SystemFilterRepo systemFilterRepo;

  @Override
  public UUID create(
          SystemFilterCreateRequest request
  ) {

    systemFilterRepo.findByNameAndLanguage(
            request.name().trim(),
            request.language().name()
    ).ifPresent(filter -> {
      throw new ApiException(ErrorCode.FILTER_ALREADY_EXISTS);
    });

    SystemFilter filter = SystemFilter.builder()
            .name(request.name().trim())
            .language(request.language().name())
            .build();

    return systemFilterRepo.save(filter).getId();
  }

  @Override
  @Transactional
  public void assignFilters(
          Grammar grammar,
          AssignFilterRequest request
  ) {

    if (request.filterIds() == null || request.filterIds().isEmpty()) {
      return;
    }

    List<SystemFilter> filters =
            systemFilterRepo.findAllById(request.filterIds());

    if (filters.size() != request.filterIds().size()) {
      throw new ApiException(ErrorCode.FILTER_NOT_FOUND);
    }

    for (UUID filterId : request.filterIds().stream().distinct().toList()) {

      grammarRepo.assignFilter(
              grammar.getId(),
              filterId
      );

    }
  }

  @Override
  @Transactional
  public void assignFilters(
          UUID grammarId,
          AssignFilterRequest request
  ) {

    Grammar grammar = grammarRepo.findById(grammarId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    UUID userId = CurrentUser.getId();

    if (!grammar.getIsPublic()
            && !grammar.getOwner().getId().equals(userId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    assignFilters(
            grammar,
            request
    );
  }

  @Override
  public ListSystemFilterResponse listSystemFiltersByLanguage(String language) {
    if (language == null || language.isBlank()) {
      throw new ApiException(ErrorCode.INVALID_LANGUAGE);
    }

    var systemFilters = systemFilterRepo.findAllByLanguage(language);

    var responses = systemFilters.stream()
            .map(systemFilter -> new ListSystemFilterResponse.SystemFilterResponse(
                    systemFilter.getId(),
                    systemFilter.getName()
            ))
            .toList();

    return new ListSystemFilterResponse(responses);
  }

}