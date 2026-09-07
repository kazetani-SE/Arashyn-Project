package com.arashi.edu.arashynbe.features.system.component.serivce;

import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.component.dto.request.ComponentCreateRequest;

import java.util.List;

public interface ComponentService {

  void createComponents(
          Grammar grammar,
          Integer groupKey,
          List<ComponentCreateRequest> requests
  );
}