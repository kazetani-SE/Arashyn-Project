package com.arashi.edu.arashynbe.features.playground.filter.service;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.SystemFilterCreateRequest;

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
}