package com.arashi.edu.arashynbe.features.system.filter.service;

import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.system.filter.dto.request.SystemFilterCreateRequest;
import com.arashi.edu.arashynbe.features.system.filter.dto.response.ListSystemFilterResponse;

import java.util.UUID;

public interface SystemFilterService {

  UUID create(
          SystemFilterCreateRequest request
  );

  void assignFilters(
          Grammar grammar,
          AssignFilterRequest request
  );

  void assignFilters(
          UUID grammarId,
          AssignFilterRequest request
  );

  ListSystemFilterResponse listSystemFiltersByLanguage(String language);
}