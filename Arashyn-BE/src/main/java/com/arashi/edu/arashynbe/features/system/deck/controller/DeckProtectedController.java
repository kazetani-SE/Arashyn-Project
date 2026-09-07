package com.arashi.edu.arashynbe.features.system.deck.controller;

import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckCreateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckUpdateRequest;
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

    if(!deckService.hasReferences(request.id())) {
      return ResponseEntity.ok(deckService.updateDeck(request));
    }

    var newRequest = new DeckCreateRequest(
            request.name(),
            request.description(),
            request.language(),
            request.isPublic(),
            request.folderId(),
            request.grammarIds()
    );

    return ResponseEntity.ok(deckService.createDeck(newRequest));
  }

  @DeleteMapping("/{deck_id}")
  public  ResponseEntity<Void> deleteDeck(@PathVariable UUID deck_id) {

    deckService.deleteDeck(deck_id);

    return ResponseEntity.noContent().build();
  }
}