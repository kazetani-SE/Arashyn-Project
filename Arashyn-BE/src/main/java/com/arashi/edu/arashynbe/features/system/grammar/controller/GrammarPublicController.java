package com.arashi.edu.arashynbe.features.system.grammar.controller;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.ExistingGrammarResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarListResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarSimilarResponse;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarService;
import com.arashi.edu.arashynbe.shared.enums.Language;
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
@RequestMapping("/public/grammar")
@RequiredArgsConstructor
public class GrammarPublicController {

  private final String PAGE_SIZE = "20";

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
          @RequestParam(defaultValue = "VI") String language,
          @RequestParam(defaultValue = PAGE_SIZE) int size,
          @RequestParam(defaultValue = "created_at") String sort,
          @RequestParam(defaultValue = "desc") String direction
  ) {
    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(
                    Sort.Direction.fromString(direction),
                    sort
            )
    );

    var lang = Language.valueOf(language);

    return ResponseEntity.ok(
            grammarService.getGrammars(lang, pageable)
      );
  }

  @GetMapping("/search")
  public GrammarListResponse search(
          @RequestParam(defaultValue = "") String query,
          @RequestParam(required = false) String filters,
          @RequestParam(required = false) String forms,
          @RequestParam(defaultValue = "true") boolean isKeyword,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = PAGE_SIZE) int size,
          @RequestParam(defaultValue = "VI") String language
  ) {
    List<String> filterIds = (filters == null || filters.isBlank())
            ? List.of()
            : Arrays.stream(filters.split(","))
            .map(String::trim)
            .filter(Predicate.not(String::isBlank))
            .toList();

    List<String> formIds = (forms == null || forms.isBlank())
            ? List.of()
            : Arrays.stream(forms.split(","))
            .map(String::trim)
            .filter(Predicate.not(String::isBlank))
            .toList();

    var lang = Language.fromCode(language);

    return grammarService.search(query, filterIds, formIds, isKeyword, lang, PageRequest.of(page, size));
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