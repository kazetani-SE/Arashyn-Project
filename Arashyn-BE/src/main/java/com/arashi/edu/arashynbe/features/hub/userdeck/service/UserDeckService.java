package com.arashi.edu.arashynbe.features.hub.userdeck.service;

import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckIdResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckListResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckMultipleResponse;

import java.util.UUID;

public interface UserDeckService {

  UserDeckIdResponse create(UserDeckCreateRequest request);

  UserDeckIdResponse clone(UserDeckCloneRequest request);

  UserDeckDuplicateCheckResponse checkDuplicate(UserDeckDuplicateCheckRequest request);

  UserGrammarDuplicateCheckMultipleResponse checkGrammarDuplicates(UUID deckId);

  UserDeckIdResponse update(UserDeckUpdateRequest request);

  UserDeckListResponse findAll();

  UserDeckListResponse findRoot();

  UserDeckDetailResponse detailById(UUID id);

  void delete(UUID id);
}