package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.filter.dto.GrammarFilterRow;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarListReadService;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningSummaryResponse;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.MeaningRepo;
import com.arashi.edu.arashynbe.repository.SystemFilterRepo;
import com.arashi.edu.arashynbe.repository.specification.GrammarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GrammarListReadServiceImpl implements GrammarListReadService {

  private final GrammarRepo grammarRepo;
  private final ComponentRepo componentRepo;
  private final MeaningRepo meaningRepo;
  private final SystemFilterRepo systemFilterRepo;

  @Override
  public GrammarListResponse getPublicGrammars(
          GrammarListRequest request,
          Pageable pageable
  ) {

    Specification<Grammar> spec = GrammarSpecification.build(request);

    Page<Grammar> grammarPage = grammarRepo.findAll(spec, pageable);

    List<Grammar> grammars = grammarPage.getContent();

    if (grammars.isEmpty()) {
      return null;
    }

    List<UUID> grammarIds = grammars.stream()
            .map(Grammar::getId)
            .toList();

    Map<UUID, List<GrammarComponentSummaryResponse>> componentMap =
            buildComponentMap(grammarIds);

    Map<UUID, List<GrammarMeaningSummaryResponse>> meaningMap =
            buildMeaningMap(grammarIds);

    Map<UUID, List<GrammarFilterResponse>> filterMap =
            buildFilterMap(grammarIds);

    List<GrammarSummaryResponse> responses =
            grammars.stream()
                    .map(grammar -> buildSummaryResponse(
                            grammar,
                            componentMap,
                            meaningMap,
                            filterMap
                    ))
                    .toList();

    return new GrammarListResponse(
            responses,
            grammarPage.getNumber(),
            grammarPage.getSize(),
            grammarPage.getTotalPages(),
            grammarPage.getTotalElements(),
            grammarPage.hasNext(),
            grammarPage.hasPrevious()
    );
  }

  private Map<UUID, List<GrammarComponentSummaryResponse>> buildComponentMap(
          List<UUID> grammarIds
  ) {

    return componentRepo
            .findByGrammarIdInOrderByGrammarIdAscGroupKeyAscOrderAsc(
                    grammarIds
            )
            .stream()
            .collect(Collectors.groupingBy(
                    component -> component.getGrammar().getId(),
                    Collectors.mapping(
                            component -> new GrammarComponentSummaryResponse(
                                    component.getGroupKey(),
                                    component.getOrder(),
                                    component.getKeyword(),
                                    component.getForm() == null
                                            ? null
                                            : component.getForm().getName()
                            ),
                            toList()
                    )
            ));
  }

  private Map<UUID, List<GrammarMeaningSummaryResponse>> buildMeaningMap(
          List<UUID> grammarIds
  ) {
    return meaningRepo
            .findAllByGrammarIdsAndGrammarOwner(grammarIds)
            .stream()
            .collect(Collectors.groupingBy(
                    meaning -> meaning.getGrammar().getId(),
                    Collectors.mapping(
                            meaning -> new GrammarMeaningSummaryResponse(
                                    meaning.getContent()
                            ),
                            toList()
                    )
            ));
  }

  private Map<UUID, List<GrammarFilterResponse>> buildFilterMap(
          List<UUID> grammarIds
  ) {

    return systemFilterRepo.findAllGrammarFilters(grammarIds)
            .stream()
            .collect(Collectors.groupingBy(
                    GrammarFilterRow::grammarId,
                    Collectors.mapping(
                            row -> new GrammarFilterResponse(
                                    row.name()
                            ),
                            toList()
                    )
            ));
  }

  private GrammarSummaryResponse buildSummaryResponse(
          Grammar grammar,
          Map<UUID, List<GrammarComponentSummaryResponse>> componentMap,
          Map<UUID, List<GrammarMeaningSummaryResponse>> meaningMap,
          Map<UUID, List<GrammarFilterResponse>> filterMap
  ) {

    return new GrammarSummaryResponse(
            grammar.getId(),
            grammar.getTitle(),
            componentMap.getOrDefault(
                    grammar.getId(),
                    List.of()
            ),
            meaningMap.getOrDefault(
                    grammar.getId(),
                    List.of()
            ),
            filterMap.getOrDefault(
                    grammar.getId(),
                    List.of()
            )
    );
  }
}