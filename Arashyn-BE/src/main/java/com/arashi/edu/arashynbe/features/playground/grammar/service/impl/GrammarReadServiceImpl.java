package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentResponse;
import com.arashi.edu.arashynbe.features.playground.example.dto.response.GrammarExampleResponse;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarReadService;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningResponse;
import com.arashi.edu.arashynbe.features.playground.note.dto.response.GrammarNoteResponse;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.ExampleRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.MeaningRepo;
import com.arashi.edu.arashynbe.repository.NoteRepo;
import com.arashi.edu.arashynbe.repository.SystemFilterRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrammarReadServiceImpl implements GrammarReadService {

  private final GrammarRepo grammarRepo;
  private final ComponentRepo componentRepo;
  private final MeaningRepo meaningRepo;
  private final ExampleRepo exampleRepo;
  private final NoteRepo noteRepo;
  private final SystemFilterRepo systemFilterRepo;

  @Override
  public Grammar requireGrammar(UUID grammarId) {
    return grammarRepo.findById(grammarId)
            .orElseThrow(() -> new ApiException(
                    ErrorCode.GRAMMAR_NOT_FOUND
            ));
  }

  @Override
  public List<GrammarComponentResponse> getComponents(UUID grammarId) {
    return componentRepo.findByGrammarIdOrderByGroupKeyAscOrderAsc(grammarId)
            .stream()
            .map(component -> new GrammarComponentResponse(
                    component.getId(),
                    component.getOrder(),
                    component.getKeyword(),
                    component.getForm() == null
                            ? null
                            : component.getForm().getName(),
                    component.getGroupKey(),
                    component.getOptional()
            ))
            .toList();
  }

  @Override
  public List<GrammarMeaningResponse> getMeanings(UUID grammarId) {
    return meaningRepo.findByGrammarIdOrderByGroupKeyAsc(grammarId)
            .stream()
            .map(meaning -> new GrammarMeaningResponse(
                    meaning.getId(),
                    meaning.getContent(),
                    meaning.getGroupKey(),
                    List.of()
            ))
            .toList();
  }

  @Override
  public Map<UUID, List<GrammarExampleResponse>> getExamples(List<UUID> meaningIds) {
    return exampleRepo.findAllByMeaningIdIn(meaningIds)
            .stream()
            .collect(Collectors.groupingBy(
                    example -> example.getMeaning().getId(),
                    Collectors.mapping(
                            example -> new GrammarExampleResponse(
                                    example.getId(),
                                    example.getSentence(),
                                    example.getTranslation(),
                                    example.getNote()
                            ),
                            Collectors.toList()
                    )
            ));
  }

  @Override
  public List<GrammarNoteResponse> getNotes(UUID grammarId) {
    return noteRepo.findAllByGrammarIdOrderByGroupKeyAsc(grammarId)
            .stream()
            .map(note -> new GrammarNoteResponse(
                    note.getId(),
                    note.getContent()
            ))
            .toList();
  }

  @Override
  public List<GrammarFilterResponse> getFilters(UUID grammarId) {
    return systemFilterRepo.findAllByGrammarId(grammarId)
            .stream()
            .map(filter -> new GrammarFilterResponse(
                    filter.getName()
            ))
            .toList();
  }

  @Override
  public List<GrammarDetailResponse.Group> buildGroups(List<GrammarComponentResponse> components, List<GrammarMeaningResponse> meanings) {
    Map<Integer, List<GrammarComponentResponse>> componentMap =
            components.stream()
                    .collect(Collectors.groupingBy(
                            c -> c.groupKey().intValue()
                    ));

    Map<Integer, List<GrammarMeaningResponse>> meaningMap =
            meanings.stream()
                    .collect(Collectors.groupingBy(
                            m -> m.groupKey().intValue()
                    ));

    Set<Integer> groupKeys = new TreeSet<>();
    groupKeys.addAll(componentMap.keySet());
    groupKeys.addAll(meaningMap.keySet());

    return groupKeys.stream()
            .map(groupKey -> new GrammarDetailResponse.Group(
                    groupKey,
                    componentMap.getOrDefault(groupKey, List.of()),
                    meaningMap.getOrDefault(groupKey, List.of())
            ))
            .toList();
  }
}