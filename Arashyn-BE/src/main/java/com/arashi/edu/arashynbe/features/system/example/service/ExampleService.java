package com.arashi.edu.arashynbe.features.system.example.service;

import com.arashi.edu.arashynbe.entity.system.Meaning;
import com.arashi.edu.arashynbe.features.system.example.dto.request.ExampleCreateRequest;

import java.util.List;
import java.util.UUID;

public interface ExampleService {

  UUID create(
          UUID meaningId,
          ExampleCreateRequest request
  );

  void createMany(
          Meaning meaning,
          List<ExampleCreateRequest> examples
  );
}