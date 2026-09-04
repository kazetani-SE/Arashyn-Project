package com.arashi.edu.arashynbe.features.playground.form.controller;

import com.arashi.edu.arashynbe.features.playground.form.dto.response.ListFormResponse;
import com.arashi.edu.arashynbe.features.playground.form.service.FormService;
import com.arashi.edu.arashynbe.shared.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public/forms")
@RequiredArgsConstructor
public class FormPublicController {

  private final FormService formService;

  @GetMapping
  public ResponseEntity<ListFormResponse> findByLanguage(@RequestParam(required = true) Language language) {
    return ResponseEntity.ok(formService.findByLanguage(language.getCode()));
  }

}