package com.arashi.edu.arashynbe.features.hub.usergrammar.service;

import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.*;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.*;

import java.util.UUID;

public interface UserGrammarService {

  UserGrammarIdResponse create(UserGrammarCreateRequest request);

  void createMultiple(UserGrammarCreateMultipleRequest request);

  UserGrammarIdResponse update(UserGrammarUpdateRequest request);

  UserGrammarDuplicateCheckResponse duplicateCheck(UserGrammarDuplicateCheckRequest request);

  UserGrammarDuplicateCheckMultipleResponse  duplicateCheckMultiple(UserGrammarDuplicateCheckMultipleRequest request);

  UserGrammarDetailResponse findById(UUID id);

  UserGrammarListResponse findAll();

  UserGrammarListResponse findAllByUserDeckId(UUID id);

  void delete(UUID id);

  void deleteRedundant();

}