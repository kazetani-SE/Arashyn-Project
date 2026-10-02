package com.arashi.edu.arashynbe.features.system.deck.controller;

import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckAssignGrammarRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckCreateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckUpdateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckCheckUpdateResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckIdResponse;
import com.arashi.edu.arashynbe.features.system.deck.service.DeckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/protected/deck")
@RequiredArgsConstructor
public class DeckProtectedController {

  private final DeckService deckService;

  @PostMapping("/create")
  public ResponseEntity<DeckIdResponse> createDeck(@Valid @RequestBody DeckCreateRequest request) {
    return ResponseEntity.ok(deckService.createDeck(request));
  }

  @PostMapping("/update")
  public ResponseEntity<DeckIdResponse> updateDeck(@Valid @RequestBody DeckUpdateRequest request) {
    return ResponseEntity.ok(deckService.updateDeck(request));
  }

  @DeleteMapping("/{deck_id}")
  public ResponseEntity<Void> deleteDeck(@PathVariable UUID deck_id) {

    deckService.deleteDeck(deck_id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/check-update/{user_deck_id}")
  public  ResponseEntity<DeckCheckUpdateResponse> checkDeckUpdate(@PathVariable UUID user_deck_id) {
    return ResponseEntity.ok(deckService.checkDeckUpdate(user_deck_id));
  }

  @PostMapping("/assign")
  public ResponseEntity<DeckIdResponse> assignDeck(@Valid @RequestBody DeckAssignGrammarRequest request) {
    return ResponseEntity.ok(deckService.assignGrammars(request));
  }
}