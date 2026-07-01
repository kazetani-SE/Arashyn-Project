package com.arashi.edu.arashynbe.features.playground.example.controller;

import com.arashi.edu.arashynbe.features.playground.example.dto.request.ExampleCreateRequest;
import com.arashi.edu.arashynbe.features.playground.example.service.ExampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/meanings/{meaningId}/examples")
@RequiredArgsConstructor
public class ExampleController {

  private final ExampleService exampleService;

  @PostMapping
  public ResponseEntity<Void> create(

          @PathVariable
          UUID meaningId,

          @Valid
          @RequestBody
          ExampleCreateRequest request

  ) {

    exampleService.create(
            meaningId,
            request
    );

    return ResponseEntity.noContent().build();
  }
}