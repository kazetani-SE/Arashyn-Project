package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.entity.Component;
import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse.GrammarSimilarItem;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarMatchService;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.shared.enums.SimilarType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GrammarMatchServiceImpl implements GrammarMatchService {

  private final ComponentRepo componentRepo;

  @Override
  public ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  ) {

    List<GrammarCreateRequest.Group> groups = request.groups();

    Set<UUID> candidateGrammarIds = intersectCandidateGrammarIds(groups);

    if (candidateGrammarIds.isEmpty()) {
      return new ExistingGrammarResponse(Optional.empty());
    }

    Map<UUID, Map<Short, List<Component>>> grammarMap =
            fetchGroupedComponents(candidateGrammarIds);

    Optional<UUID> matchedGrammarId = grammarMap.entrySet().stream()
            .filter(entry -> compareGrammar(groups, entry.getValue()) == SimilarType.FULL)
            .map(Map.Entry::getKey)
            .findFirst();

    return new ExistingGrammarResponse(matchedGrammarId);
  }

  @Override
  public GrammarSimilarResponse findSimilarGrammar(
          @Valid GrammarCreateRequest request
  ) {

    List<GrammarCreateRequest.Group> groups = request.groups();

    // Similar grammar only needs at least one matching anchor -> union
    Set<UUID> candidateGrammarIds = unionCandidateGrammarIds(groups);

    if (candidateGrammarIds.isEmpty()) {
      return new GrammarSimilarResponse(List.of());
    }

    Map<UUID, Map<Short, List<Component>>> grammarMap =
            fetchGroupedComponents(candidateGrammarIds);

    List<GrammarSimilarItem> matches = grammarMap.entrySet().stream()
            .map(entry -> new GrammarSimilarItem(
                    entry.getKey(),
                    compareGrammar(groups, entry.getValue())
            ))
            .filter(item -> item.type().isDuplicate())
            .toList();

    return new GrammarSimilarResponse(matches);
  }

  /**
   * Get grammar IDs that contain anchors from ALL request groups (Intersection).
   * Used for exact match detection.
   */
  private Set<UUID> intersectCandidateGrammarIds(
          List<GrammarCreateRequest.Group> groups
  ) {

    Set<UUID> result = null;

    for (GrammarCreateRequest.Group group : groups) {

      ComponentCreateRequest anchor = findAnchor(group.components());
      List<UUID> currentIds = findCandidateGrammarIds(anchor);

      if (currentIds.isEmpty()) {
        return Set.of();
      }

      if (result == null) {
        result = new HashSet<>(currentIds);
      } else {
        result.retainAll(currentIds);
      }

      if (result.isEmpty()) {
        return Set.of();
      }
    }

    return result == null ? Set.of() : result;
  }

  /**
   * Get grammar IDs that contain anchors from AT LEAST ONE request group (Union).
   * Used for partial/similar match detection.
   */
  private Set<UUID> unionCandidateGrammarIds(
          List<GrammarCreateRequest.Group> groups
  ) {

    Set<UUID> result = new HashSet<>();

    for (GrammarCreateRequest.Group group : groups) {
      ComponentCreateRequest anchor = findAnchor(group.components());
      result.addAll(findCandidateGrammarIds(anchor));
    }

    return result;
  }

  private ComponentCreateRequest findAnchor(
          List<ComponentCreateRequest> components
  ) {
    return components.stream()
            .filter(component -> component.keyword() != null)
            .findFirst()
            .orElse(components.getFirst());
  }

  private List<UUID> findCandidateGrammarIds(
          ComponentCreateRequest anchor
  ) {

    if (anchor.keyword() != null) {
      return componentRepo.findGrammarIdsByKeywordAndOrder(
              anchor.keyword(),
              anchor.order().shortValue()
      );
    }

    return componentRepo.findGrammarIdsByFormAndOrder(
            anchor.formId(),
            anchor.order().shortValue()
    );
  }

  private Map<UUID, Map<Short, List<Component>>> fetchGroupedComponents(
          Set<UUID> grammarIds
  ) {

    List<Component> components =
            componentRepo.findByGrammarIdInOrderByGrammarIdAscOrderAsc(
                    new ArrayList<>(grammarIds)
            );

    return components.stream()
            .collect(Collectors.groupingBy(
                    component -> component.getGrammar().getId(),
                    Collectors.groupingBy(Component::getGroupKey)
            ));
  }

  /**
   * Compare a candidate grammar with the request:
   * - FULL: Group count matches AND all groups match exactly.
   * - PARTIAL: At least one group matches exactly, but not FULL.
   * - NONE: No groups match.
   */
  private SimilarType compareGrammar(
          List<GrammarCreateRequest.Group> requestGroups,
          Map<Short, List<Component>> candidateGroups
  ) {

    boolean anyGroupMatched = false;
    boolean allGroupsMatched = candidateGroups.size() == requestGroups.size();

    for (GrammarCreateRequest.Group requestGroup : requestGroups) {

      boolean matched = isGroupMatch(requestGroup, candidateGroups);

      if (matched) {
        anyGroupMatched = true;
      } else {
        allGroupsMatched = false;
      }
    }

    if (allGroupsMatched) {
      return SimilarType.FULL;
    }

    if (anyGroupMatched) {
      return SimilarType.PARTIAL;
    }

    return SimilarType.NONE;
  }

  private boolean isGroupMatch(
          GrammarCreateRequest.Group requestGroup,
          Map<Short, List<Component>> candidateGroups
  ) {

    List<Component> candidateGroup =
            candidateGroups.get(requestGroup.groupKey().shortValue());

    if (candidateGroup == null) {
      return false;
    }

    List<ComponentCreateRequest> requestComponents = requestGroup.components();

    if (candidateGroup.size() != requestComponents.size()) {
      return false;
    }

    for (int i = 0; i < requestComponents.size(); i++) {
      if (!isComponentMatch(requestComponents.get(i), candidateGroup.get(i))) {
        return false;
      }
    }

    return true;
  }

  private boolean isComponentMatch(
          ComponentCreateRequest request,
          Component candidate
  ) {

    if (!request.order().equals(candidate.getOrder())) {
      return false;
    }

    if (request.optional() != candidate.getOptional()) {
      return false;
    }

    if (request.keyword() != null) {
      return request.keyword().equals(candidate.getKeyword());
    }

    return request.formId().equals(candidate.getForm().getId());
  }
}