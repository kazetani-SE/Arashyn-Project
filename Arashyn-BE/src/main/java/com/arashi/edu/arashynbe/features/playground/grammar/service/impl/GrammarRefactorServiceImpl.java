package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.entity.Component;
import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarRefactorService;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GrammarRefactorServiceImpl implements GrammarRefactorService {

  private final ComponentRepo componentRepo;

  @Override
  public ExistingGrammarResponse findExistingGrammar(
          @Valid GrammarCreateRequest request
  ) {

    Set<UUID> candidateGrammarIds = null;

    for (GrammarCreateRequest.Group group : request.groups()) {

      ComponentCreateRequest anchor = findAnchor(group.components());

      List<UUID> currentCandidateIds = findCandidateGrammarIds(anchor);

      if (currentCandidateIds.isEmpty()) {
        return new ExistingGrammarResponse(Optional.empty());
      }

      if (candidateGrammarIds == null) {
        candidateGrammarIds = new HashSet<>(currentCandidateIds);
      } else {
        candidateGrammarIds.retainAll(currentCandidateIds);
      }

      if (candidateGrammarIds.isEmpty()) {
        return new ExistingGrammarResponse(Optional.empty());
      }
    }

    List<Component> candidateComponents =
            componentRepo.findByGrammarIdInOrderByGrammarIdAscOrderAsc(
                    new ArrayList<>(candidateGrammarIds)
            );

    return new ExistingGrammarResponse(
            findMatchingGrammar(request.groups(), candidateComponents)
    );
  }

  private ComponentCreateRequest findAnchor(
          List<ComponentCreateRequest> components
  ) {
    return components.stream()
            .filter(component -> component.keyWord() != null)
            .findFirst()
            .orElse(components.getFirst());
  }

  private List<UUID> findCandidateGrammarIds(
          ComponentCreateRequest anchor
  ) {

    if (anchor.keyWord() != null) {
      return componentRepo.findGrammarIdsByKeywordAndOrder(
              anchor.keyWord(),
              anchor.order().shortValue()
      );
    }

    return componentRepo.findGrammarIdsByFormAndOrder(
            anchor.formId(),
            anchor.order().shortValue()
    );
  }

  private Optional<UUID> findMatchingGrammar(
          List<GrammarCreateRequest.Group> requestGroups,
          List<Component> candidateComponents
  ) {

    Map<UUID, Map<Short, List<Component>>> grammarMap =
            candidateComponents.stream()
                    .collect(Collectors.groupingBy(
                            component -> component.getGrammar().getId(),
                            Collectors.groupingBy(Component::getGroupKey)
                    ));

    for (Map.Entry<UUID, Map<Short, List<Component>>> entry : grammarMap.entrySet()) {

      UUID grammarId = entry.getKey();
      Map<Short, List<Component>> candidateGroups = entry.getValue();

      // Different number of groups
      if (candidateGroups.size() != requestGroups.size()) {
        continue;
      }

      boolean grammarMatched = true;

      for (GrammarCreateRequest.Group requestGroup : requestGroups) {

        List<Component> candidateGroup =
                candidateGroups.get(requestGroup.groupKey().shortValue());

        if (candidateGroup == null) {
          grammarMatched = false;
          break;
        }

        List<ComponentCreateRequest> requestComponents = requestGroup.components();

        // Different number of components
        if (candidateGroup.size() != requestComponents.size()) {
          grammarMatched = false;
          break;
        }

        candidateGroup.sort(Comparator.comparing(Component::getOrder));

        boolean groupMatched = true;

        for (int i = 0; i < requestComponents.size(); i++) {

          ComponentCreateRequest request = requestComponents.get(i);
          Component candidate = candidateGroup.get(i);

          // Compare order
          if (!request.order().equals(candidate.getOrder())) {
            groupMatched = false;
            break;
          }

          // Compare optional
          if (request.optional() != candidate.getOptional()) {
            groupMatched = false;
            break;
          }

          // Compare keyword
          if (request.keyWord() != null) {

            if (!request.keyWord().equals(candidate.getKeyword())) {
              groupMatched = false;
            }

          } else {

            if (!request.formId().equals(candidate.getForm().getId())) {
              groupMatched = false;
            }

          }

          if (!groupMatched) {
            break;
          }
        }

        if (!groupMatched) {
          grammarMatched = false;
          break;
        }
      }

      if (grammarMatched) {
        return Optional.of(grammarId);
      }
    }

    return Optional.empty();
  }
}