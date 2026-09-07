package com.arashi.edu.arashynbe.features.system.deck.service;

import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckCreateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.request.DeckUpdateRequest;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckDetailResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckIdResponse;
import com.arashi.edu.arashynbe.features.system.deck.dto.response.DeckListResponse;

import java.util.UUID;

public interface DeckService {

  DeckIdResponse createDeck(DeckCreateRequest request);

  DeckIdResponse updateDeck(DeckUpdateRequest request);

  DeckListResponse listDecks();

  DeckDetailResponse findDeckById(UUID id);

  boolean hasReferences(UUID id);

  void  deleteDeck(UUID id);
}