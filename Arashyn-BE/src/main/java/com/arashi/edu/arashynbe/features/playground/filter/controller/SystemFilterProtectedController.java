package com.arashi.edu.arashynbe.features.playground.filter.controller;

import com.arashi.edu.arashynbe.features.playground.filter.dto.request.SystemFilterCreateRequest;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/protected/system-filters")
@RequiredArgsConstructor
public class SystemFilterProtectedController {

  private final SystemFilterService systemFilterService;

  @PostMapping
  public UUID create(

          @Valid
          @RequestBody
          SystemFilterCreateRequest request

  ) {

    return systemFilterService.create(request);

  }

}