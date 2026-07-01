package com.arashi.edu.arashynbe.features.playground.meaning.controller;

import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammar/{grammarId}/meanings")
@RequiredArgsConstructor
public class MeaningController {

  private final MeaningService meaningService;

  @PostMapping
  public UUID create(

          @PathVariable
          UUID grammarId,

          @Valid
          @RequestBody
          MeaningCreateRequest request

  ) {

    return meaningService.create(
            grammarId,
            request
    );

  }

}