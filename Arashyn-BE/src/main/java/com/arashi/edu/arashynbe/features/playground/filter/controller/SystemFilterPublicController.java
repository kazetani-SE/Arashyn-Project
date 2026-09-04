package com.arashi.edu.arashynbe.features.playground.filter.controller;

import com.arashi.edu.arashynbe.features.playground.filter.dto.response.ListSystemFilterResponse;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.shared.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/system-filters")
@RequiredArgsConstructor
public class SystemFilterPublicController {

  private final SystemFilterService systemFilterService;

  @GetMapping
  public ResponseEntity<ListSystemFilterResponse> getAllSystemFilters(@RequestParam(required = true) Language language) {
    return ResponseEntity.ok(systemFilterService.listSystemFiltersByLanguage(language.getCode()));
  }
}