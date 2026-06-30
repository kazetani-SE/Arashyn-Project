package com.arashi.edu.arashynbe.features.playground.form.controller;

import com.arashi.edu.arashynbe.features.playground.form.dto.request.FormCreateRequest;
import com.arashi.edu.arashynbe.features.playground.form.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contributor/forms")
@RequiredArgsConstructor
public class FormController {

  private final FormService formService;

  @PostMapping
  public UUID create(
          @Valid @RequestBody FormCreateRequest request
  ) {
    return formService.create(request);
  }

}