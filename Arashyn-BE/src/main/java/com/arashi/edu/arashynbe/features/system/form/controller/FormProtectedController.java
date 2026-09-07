package com.arashi.edu.arashynbe.features.system.form.controller;

import com.arashi.edu.arashynbe.features.system.form.dto.request.FormCreateRequest;
import com.arashi.edu.arashynbe.features.system.form.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/protected/forms")
@RequiredArgsConstructor
public class FormProtectedController {

  private final FormService formService;

  @PostMapping
  public ResponseEntity<UUID> create(
          @Valid @RequestBody FormCreateRequest request
  ) {
    return ResponseEntity.ok(formService.create(request));
  }

}