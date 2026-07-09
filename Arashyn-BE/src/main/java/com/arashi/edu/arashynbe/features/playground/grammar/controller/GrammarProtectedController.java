package com.arashi.edu.arashynbe.features.playground.grammar.controller;

import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammar-protected")
@RequiredArgsConstructor
public class GrammarProtectedController {

  private final GrammarService grammarService;
  private final SystemFilterService systemFilterService;

  @PostMapping("/create")
  public ResponseEntity<String> create(
          @Valid @RequestBody GrammarCreateRequest request
  ) {
    return ResponseEntity.ok(
              grammarService.createNewGrammar(request)
    );
  }

  @PostMapping("/{grammarId}/filters")
  public ResponseEntity<Void> assignFilters(

          @PathVariable
          UUID grammarId,

          @Valid
          @RequestBody
          AssignFilterRequest request

  ) {

    systemFilterService.assignFilters(
            grammarId,
            request
    );

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{grammarId}")
  public ResponseEntity<Void> deleteGrammar(
          @PathVariable UUID grammarId
  ) {

    grammarService.deleteGrammar(grammarId);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{grammarId}/restore")
  public ResponseEntity<Void> restoreGrammar(
          @PathVariable UUID grammarId
  ) {

    grammarService.restoreGrammar(grammarId);

    return ResponseEntity.noContent().build();
  }
}