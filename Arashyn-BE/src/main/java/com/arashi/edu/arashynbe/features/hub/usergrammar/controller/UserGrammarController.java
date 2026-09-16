package com.arashi.edu.arashynbe.features.hub.usergrammar.controller;

import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.*;
import com.arashi.edu.arashynbe.features.hub.usergrammar.service.UserGrammarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user_grammar")
@RequiredArgsConstructor
public class UserGrammarController {

  private final UserGrammarService userGrammarService;

  @PostMapping("/check_duplicate")
  public ResponseEntity<UserGrammarDuplicateCheckResponse> checkDuplicate(@Valid @RequestBody UserGrammarDuplicateCheckRequest request) {
    return ResponseEntity.ok(userGrammarService.duplicateCheck(request));
  }

  @PostMapping("/check_duplicate_multiple")
  public ResponseEntity<UserGrammarDuplicateCheckMultipleResponse> checkDuplicateMultiple(@Valid @RequestBody UserGrammarDuplicateCheckMultipleRequest request) {
    return ResponseEntity.ok(userGrammarService.duplicateCheckMultiple(request));
  }

  @PostMapping
  public ResponseEntity<UserGrammarIdResponse> create(@Valid @RequestBody UserGrammarCreateRequest request){
    return ResponseEntity.ok(userGrammarService.create(request));
  }

  @PutMapping
  public ResponseEntity<UserGrammarIdResponse> update(@Valid @RequestBody UserGrammarUpdateRequest request){
    return ResponseEntity.ok(userGrammarService.update(request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id){
    userGrammarService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserGrammarDetailResponse> detail(@PathVariable UUID id){
    return ResponseEntity.ok(userGrammarService.findById(id));
  }

  @GetMapping()
  public ResponseEntity<UserGrammarListResponse> list(){
    return ResponseEntity.ok(userGrammarService.findAll());
  }
}