package com.arashi.edu.arashynbe.features.hub.usergrammar.service;

import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDetailResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckMultipleResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarIdResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarListResponse;

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