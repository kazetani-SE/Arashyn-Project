package com.arashi.edu.arashynbe.features.learning.proficiency.controller;

import com.arashi.edu.arashynbe.features.learning.proficiency.dto.UpdateProficiencyRequest;
import com.arashi.edu.arashynbe.features.learning.proficiency.service.ProficiencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/proficiency")
@RequiredArgsConstructor
public class ProficiencyController {

  private final ProficiencyService proficiencyService;

  @PatchMapping("")
  public ResponseEntity<Void> update(@RequestBody @Valid UpdateProficiencyRequest request) {

    proficiencyService.update(request.userGrammarId(), request.changes());

    return ResponseEntity.ok().build();
  }

}