package com.arashi.edu.arashynbe.features.hub.userdeck.service;

import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckIdResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckListResponse;

import java.util.UUID;

public interface UserDeckService {

  UserDeckIdResponse create(UserDeckCreateRequest request);

  UserDeckDuplicateCheckResponse checkDuplicate(UserDeckDuplicateCheckRequest request);

  UserDeckIdResponse update(UserDeckUpdateRequest request);

  UserDeckListResponse findAll();

  UserDeckDetailResponse findById(UUID id);

  void delete(UUID id);
}