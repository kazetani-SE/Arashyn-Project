package com.arashi.edu.arashynbe.features.system.deck.controller;

import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckDetailResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;
import com.arashi.edu.arashynbe.features.system.deck.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/public/deck")
@RequiredArgsConstructor
public class DeckPublicController {

  private final DeckService deckService;

  @GetMapping
  public ResponseEntity<DeckListResponse> getDeckList() {
    return ResponseEntity.ok(deckService.listDecks());
  }

  @GetMapping("/{deck_id}")
  public ResponseEntity<DeckDetailResponse> getDeck(@PathVariable UUID deck_id) {
    return ResponseEntity.ok(deckService.findDeckById(deck_id));
  }

}