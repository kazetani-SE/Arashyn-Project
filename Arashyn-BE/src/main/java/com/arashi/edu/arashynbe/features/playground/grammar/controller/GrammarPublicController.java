package com.arashi.edu.arashynbe.features.playground.grammar.controller;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammar-public")
@RequiredArgsConstructor
public class GrammarPublicController {

  private final GrammarService grammarService;

  @GetMapping("/{grammarId}")
  public ResponseEntity<GrammarDetailResponse> getDetail(
          @PathVariable UUID grammarId
  ) {
    return ResponseEntity.ok(
            grammarService.getDetail(grammarId)
    );
  }

}