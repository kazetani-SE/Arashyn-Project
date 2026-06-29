package com.arashi.edu.arashynbe.features.playground.grammar.controller;

import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grammar-protected")
@RequiredArgsConstructor
public class GrammarProtectedController {

  private final GrammarService grammarService;

  @PostMapping("/create")
  public ResponseEntity<String> create(
          @Valid @RequestBody GrammarCreateRequest request
  ) {
    return ResponseEntity.ok(
              grammarService.createNewGrammar(request)
    );
  }


}