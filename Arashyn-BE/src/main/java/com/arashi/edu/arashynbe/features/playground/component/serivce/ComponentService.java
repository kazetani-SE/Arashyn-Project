package com.arashi.edu.arashynbe.features.playground.component.serivce;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;

import java.util.List;

public interface ComponentService {

  void createComponents(
          Grammar grammar,
          Integer groupKey,
          List<ComponentCreateRequest> requests
  );
}