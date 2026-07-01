package com.arashi.edu.arashynbe.features.playground.meaning.service;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;

import java.util.List;
import java.util.UUID;

public interface MeaningService {

  UUID create(
          UUID grammarId,
          MeaningCreateRequest request
  );

  void createMany(
          Grammar grammar,
          Integer groupKey,
          List<String> meanings
  );
}