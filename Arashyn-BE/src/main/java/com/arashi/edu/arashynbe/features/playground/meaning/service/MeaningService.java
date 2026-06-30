package com.arashi.edu.arashynbe.features.playground.meaning.service;

import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;

import java.util.UUID;

public interface MeaningService {

  UUID create(
          UUID grammarId,
          MeaningCreateRequest request
  );

}