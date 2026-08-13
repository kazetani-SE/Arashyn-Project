package com.arashi.edu.arashynbe.features.playground.grammar.controller;

import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("/grammar-public")
@RequiredArgsConstructor
public class GrammarPublicController {

  private final int PAGE_SIZE = 20;

  private final GrammarService grammarService;

  @PostMapping
  public ResponseEntity<GrammarListResponse> getPublicGrammars(
          @RequestBody GrammarListRequest request,
          @RequestParam(defaultValue = "0") Integer page
  ) {

    return ResponseEntity.ok(
            grammarService.getPublicGrammars(request, page)
    );
  }

  @GetMapping("/item_list/grammar")
  public ResponseEntity<GrammarListResponse> getItems(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "created_at") String sort,
          @RequestParam(defaultValue = "desc") String direction
  ) {
    Pageable pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by(
                    Sort.Direction.fromString(direction),
                    sort
            )
    );

    return ResponseEntity.ok(
            grammarService.getGrammars(pageable)
      );
  }

  @GetMapping("/search")
  public GrammarListResponse search(
          @RequestParam(defaultValue = "") String query,
          @RequestParam(required = false) String filters,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "20") int size
  ) {
    List<String> filterIds = (filters == null || filters.isBlank())
            ? List.of()
            : Arrays.stream(filters.split(","))
            .map(String::trim)
            .filter(Predicate.not(String::isBlank))
            .toList();

    return grammarService.search(query, filterIds, PageRequest.of(page, size));
  }

  @GetMapping("/{grammarId}")
  public ResponseEntity<GrammarDetailResponse> getDetail(
          @PathVariable UUID grammarId
  ) {
    return ResponseEntity.ok(
            grammarService.getDetail(grammarId)
    );
  }

  @PostMapping("/check-exist")
  public ResponseEntity<ExistingGrammarResponse> checkGrammarExist(
          @RequestBody @Valid GrammarCreateRequest request
  ){

    return ResponseEntity.ok(
            grammarService.findExistingGrammar(request)
    );

  }

  @PostMapping("/similar")
  public ResponseEntity<GrammarSimilarResponse> checkSimilarGrammar(
          @RequestBody @Valid GrammarCreateRequest request
  ){

    return ResponseEntity.ok(
            grammarService.findSimilarGrammar(request)
    );

  }
}