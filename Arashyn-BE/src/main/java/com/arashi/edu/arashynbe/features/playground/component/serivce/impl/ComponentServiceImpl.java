package com.arashi.edu.arashynbe.features.playground.component.serivce.impl;


import com.arashi.edu.arashynbe.entity.Component;
import com.arashi.edu.arashynbe.entity.Form;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.playground.component.serivce.ComponentService;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.FormRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

  private final ComponentRepo componentRepo;
  private final FormRepo formRepo;

  @Override
  public void createComponents(
          Grammar grammar,
          Integer groupKey,
          List<ComponentCreateRequest> requests
  ) {

    List<Component> components = new ArrayList<>();

    for (ComponentCreateRequest request : requests) {

      boolean hasKeyword =
              request.keyword() != null &&
                      !request.keyword().isBlank();

      boolean hasForm =
              request.formId() != null;

      // Exactly one must exist
      if (hasKeyword == hasForm) {
        throw new ApiException(ErrorCode.INVALID_COMPONENT_REFERENCE);
      }

      Form form = null;

      if (hasForm) {
        form = formRepo.findById(request.formId())
                .orElseThrow(() ->
                        new ApiException(ErrorCode.FORM_NOT_FOUND));
      }

      Component component = Component.builder()
              .grammar(grammar)
              .form(form)
              .order(request.order())
              .keyword(hasKeyword ? request.keyword().trim() : null)
              .optional(request.optional())
              .groupKey(groupKey.shortValue())
              .build();

      components.add(component);
    }

    componentRepo.saveAll(components);
  }
}