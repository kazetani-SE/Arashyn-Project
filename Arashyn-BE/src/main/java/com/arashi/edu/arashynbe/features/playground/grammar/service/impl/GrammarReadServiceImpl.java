package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentEditResponse;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentResponse;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.example.dto.request.ExampleCreateRequest;
import com.arashi.edu.arashynbe.features.playground.example.dto.response.GrammarExampleEditResponse;
import com.arashi.edu.arashynbe.features.playground.example.dto.response.GrammarExampleResponse;
import com.arashi.edu.arashynbe.features.playground.filter.dto.GrammarFilterRow;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarEditResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarReadService;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateBase;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningEditResponse;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningResponse;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningSummaryResponse;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.features.playground.note.dto.response.GrammarNoteEditResponse;
import com.arashi.edu.arashynbe.features.playground.note.dto.response.GrammarNoteResponse;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.ExampleRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.MeaningRepo;
import com.arashi.edu.arashynbe.repository.NoteRepo;
import com.arashi.edu.arashynbe.repository.SystemFilterRepo;
import com.arashi.edu.arashynbe.repository.specification.GrammarSpecification;
import com.arashi.edu.arashynbe.shared.enums.Language;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
  public GrammarDetailResponse getDetail(UUID grammarId) {

    Grammar grammar = requireGrammar(grammarId);

    var components =
            getComponents(grammarId);

    var meanings =
            getMeanings(grammarId);

    var examples =
            getExamples(
                    meanings.stream()
                            .map(GrammarMeaningResponse::id)
                            .toList()
            );

    meanings = attachExamples(
            meanings,
            examples
    );

    return new GrammarDetailResponse(
            grammar.getId(),
            grammar.getTitle(),
            Language.valueOf(grammar.getLanguage()),
            grammar.getIsPublic(),
            grammar.getOwner().getId(),
            grammar.getOwner().getUsername(),
            buildGroups(
                    components,
                    meanings
            ),
            getNotes(grammarId),
            getFilters(grammarId)
    );
  }

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

  @Override
  public GrammarEditResponse getEditDetail(UUID grammarId) {

    Grammar grammar = requireGrammar(grammarId);

    UUID currentUserId = CurrentUser.getId();

    if (grammar.getOwner() == null
            || !grammar.getOwner().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    var components = getEditComponents(grammarId);

    var meanings = getEditMeanings(
            grammarId,
            currentUserId
    );

    var examples = getEditExamples(
            meanings.stream()
                    .map(GrammarMeaningEditResponse::id)
                    .toList()
    );

    meanings = attachEditExamples(
            meanings,
            examples
    );

    GrammarCreateRequest detail = new GrammarCreateRequest(
            grammar.getTitle(),
            Language.valueOf(grammar.getLanguage()),
            grammar.getIsPublic(),
            buildEditGroups(
                    components,
                    meanings
            ),
            getEditNotes(grammarId, currentUserId)
                    .stream()
                    .map(note -> new NoteCreateRequest(
                            note.content(),
                            note.isPublic(),
                            note.groupKey()
                    ))
                    .toList(),
            null
    );

    return new GrammarEditResponse(
            grammar.getId(),
            detail
    );
  }

  private Grammar requireGrammar(UUID grammarId) {
    return grammarRepo.findById(grammarId)
            .orElseThrow(() -> new ApiException(
                    ErrorCode.GRAMMAR_NOT_FOUND
            ));
  }

  private List<GrammarComponentResponse> getComponents(UUID grammarId) {
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

  private List<GrammarMeaningResponse> getMeanings(UUID grammarId) {
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

  private Map<UUID, List<GrammarExampleResponse>> getExamples(List<UUID> meaningIds) {
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
                            toList()
                    )
            ));
  }

  private List<GrammarNoteResponse> getNotes(UUID grammarId) {
    return noteRepo.findAllByGrammarIdOrderByGroupKeyAsc(grammarId)
            .stream()
            .map(note -> new GrammarNoteResponse(
                    note.getId(),
                    note.getContent()
            ))
            .toList();
  }

  private List<GrammarFilterResponse> getFilters(UUID grammarId) {
    return systemFilterRepo.findAllByGrammarId(grammarId)
            .stream()
            .map(filter -> new GrammarFilterResponse(
                    filter.getName()
            ))
            .toList();
  }

  private List<GrammarDetailResponse.Group> buildGroups(List<GrammarComponentResponse> components, List<GrammarMeaningResponse> meanings) {
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

  private List<GrammarMeaningResponse> attachExamples(
          List<GrammarMeaningResponse> meanings,
          Map<UUID, List<GrammarExampleResponse>> examples
  ) {

    return meanings.stream()
            .map(meaning -> new GrammarMeaningResponse(
                    meaning.id(),
                    meaning.content(),
                    meaning.groupKey(),
                    examples.getOrDefault(
                            meaning.id(),
                            List.of()
                    )
            ))
            .toList();
  }



  private List<GrammarComponentEditResponse> getEditComponents(UUID grammarId){
    return componentRepo.findByGrammarIdOrderByGroupKeyAscOrderAsc(grammarId)
            .stream()
            .map(component -> new GrammarComponentEditResponse(
                    component.getId(),
                    component.getOrder(),
                    component.getKeyword(),
                    component.getForm() == null
                            ? null
                            : component.getForm().getId(),
                    component.getGroupKey(),
                    component.getOptional()
            ))
            .toList();
  }

  private List<GrammarMeaningEditResponse> getEditMeanings(
          UUID grammarId,
          UUID ownerId
  ){
    return meaningRepo.findByGrammarIdAndOwnerIdOrderByGroupKeyAsc(grammarId, ownerId)
            .stream()
            .map(meaning -> new GrammarMeaningEditResponse(
                    meaning.getId(),
                    meaning.getContent(),
                    meaning.getGroupKey(),
                    meaning.getIsPublic(),
                    List.of()
            ))
            .toList();
  }

  private Map<UUID, List<GrammarExampleEditResponse>> getEditExamples(List<UUID> meaningIds) {
    return exampleRepo.findAllByMeaningIdIn(meaningIds)
            .stream()
            .collect(Collectors.groupingBy(
                    example -> example.getMeaning().getId(),
                    Collectors.mapping(
                            example -> new GrammarExampleEditResponse(
                                    example.getId(),
                                    example.getSentence(),
                                    example.getTranslation(),
                                    example.getNote(),
                                    example.getIsPublic()
                            ),
                            toList()
                    )
            ));
  }

  private List<GrammarNoteEditResponse> getEditNotes(UUID grammarId, UUID ownerId) {
    return noteRepo.findByGrammarIdAndOwnerIdOrderByGroupKeyAsc(grammarId, ownerId)
            .stream()
            .map(note -> new GrammarNoteEditResponse(
                    note.getId(),
                    note.getContent(),
                    note.getGroupKey(),
                    note.getIsPublic()
            ))
            .toList();
  }

  private List<GrammarMeaningEditResponse> attachEditExamples(
          List<GrammarMeaningEditResponse> meanings,
          Map<UUID, List<GrammarExampleEditResponse>> examples
  ) {

    return meanings.stream()
            .map(meaning -> new GrammarMeaningEditResponse(
                    meaning.id(),
                    meaning.content(),
                    meaning.groupKey(),
                    meaning.isPublic(),
                    examples.getOrDefault(
                            meaning.id(),
                            List.of()
                    )
            ))
            .toList();
  }

  private List<GrammarCreateRequest.Group> buildEditGroups(
          List<GrammarComponentEditResponse> components,
          List<GrammarMeaningEditResponse> meanings
  ) {

    Map<Integer, List<GrammarComponentEditResponse>> componentMap =
            components.stream()
                    .collect(Collectors.groupingBy(c -> c.groupKey().intValue()));

    Map<Integer, List<GrammarMeaningEditResponse>> meaningMap =
            meanings.stream()
                    .collect(Collectors.groupingBy(m -> m.groupKey().intValue()));

    Set<Integer> groupKeys = new TreeSet<>();
    groupKeys.addAll(componentMap.keySet());
    groupKeys.addAll(meaningMap.keySet());

    return groupKeys.stream()
            .map(groupKey -> new GrammarCreateRequest.Group(
                    groupKey,
                    toComponentRequests(
                            componentMap.getOrDefault(groupKey, List.of())
                    ),
                    toMeaningRequests(
                            meaningMap.getOrDefault(groupKey, List.of())
                    )
            ))
            .toList();
  }

  private List<ComponentCreateRequest> toComponentRequests(
          List<GrammarComponentEditResponse> components
  ) {
    return components.stream()
            .map(component -> new ComponentCreateRequest(
                    component.order(),
                    component.formId(),
                    component.keyword(),
                    component.optional()
            ))
            .toList();
  }

  private List<MeaningCreateBase> toMeaningRequests(
          List<GrammarMeaningEditResponse> meanings
  ) {
    return meanings.stream()
            .map(meaning -> new MeaningCreateBase(
                    meaning.content(),
                    meaning.isPublic(),
                    toExampleRequests(meaning.examples())
            ))
            .toList();
  }

  private List<ExampleCreateRequest> toExampleRequests(
          List<GrammarExampleEditResponse> examples
  ) {
    return examples.stream()
            .map(example -> new ExampleCreateRequest(
                    example.sentence(),
                    example.translation(),
                    example.note(),
                    example.isPublic()
            ))
            .toList();
  }

  private Map<UUID, List<GrammarComponentSummaryResponse>> buildComponentMap(
          List<UUID> grammarIds
  ) {

    return componentRepo
            .findByGrammarIdInAndGroupKeyOrderByGrammarIdAscOrderAsc(
                    grammarIds,
                    (short) 1
            )
            .stream()
            .collect(Collectors.groupingBy(
                    component -> component.getGrammar().getId(),
                    Collectors.mapping(
                            component -> new GrammarComponentSummaryResponse(
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
            .findByGrammarIdInAndGroupKeyOrderByGrammarIdAsc(
                    grammarIds,
                    (short) 1
            )
            .stream()
            .collect(Collectors.groupingBy(
                    meaning -> meaning.getGrammar().getId(),
                    Collectors.collectingAndThen(
                            Collectors.mapping(
                                    meaning -> new GrammarMeaningSummaryResponse(
                                            meaning.getContent()
                                    ),
                                    toList()
                            ),
                            list -> list.stream()
                                    .limit(2)
                                    .toList()
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