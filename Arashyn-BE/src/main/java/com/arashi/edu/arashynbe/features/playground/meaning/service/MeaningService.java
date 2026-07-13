package com.arashi.edu.arashynbe.features.playground.meaning.service;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningTransferRefRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateBase;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;

import java.util.List;
import java.util.UUID;

public interface MeaningService {

  void create(
          UUID grammarId,
          MeaningCreateRequest request
  );

  void createMany(
          Grammar grammar,
          Integer groupKey,
          List<MeaningCreateBase> meaning
  );

  void transferReference(
          MeaningTransferRefRequest request
  );
}