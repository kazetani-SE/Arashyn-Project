package com.arashi.edu.arashynbe.features.playground.meaning.controller;

import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammar/{grammarId}/meanings")
@RequiredArgsConstructor
public class MeaningController {

  private final MeaningService meaningService;

  @PostMapping
  public ResponseEntity<Void> create(

          @PathVariable
          UUID grammarId,

          @Valid
          @RequestBody
          MeaningCreateRequest request

  ) {

    meaningService.create(
            grammarId,
            request
    );

    return ResponseEntity.noContent().build();
  }
}