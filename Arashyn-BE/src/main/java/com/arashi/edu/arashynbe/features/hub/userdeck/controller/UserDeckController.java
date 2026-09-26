package com.arashi.edu.arashynbe.features.hub.userdeck.controller;

import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.request.UserDeckUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckIdResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckListResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.service.UserDeckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user_deck")
@RequiredArgsConstructor
public class UserDeckController {

  private final UserDeckService userDeckService;

  // C
  @PostMapping("/check_duplicate")
  public ResponseEntity<UserDeckDuplicateCheckResponse> checkDuplicate(@Valid @RequestBody UserDeckDuplicateCheckRequest request) {
    return ResponseEntity.ok(userDeckService.checkDuplicate(request));
  }

  @PostMapping
  public ResponseEntity<UserDeckIdResponse> create(@Valid @RequestBody UserDeckCreateRequest request) {
    return ResponseEntity.ok(userDeckService.create(request));
  }

  @PostMapping("/clone")
  public ResponseEntity<UserDeckIdResponse> clone(@Valid @RequestBody UserDeckCloneRequest request){
    return ResponseEntity.ok(userDeckService.clone(request));
  }

  // U
  @PutMapping
  public ResponseEntity<UserDeckIdResponse> update(@Valid @RequestBody UserDeckUpdateRequest request){
    return ResponseEntity.ok(userDeckService.update(request));
  }

  // D
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id){
    userDeckService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // R
  @GetMapping("/{id}")
  public ResponseEntity<UserDeckDetailResponse> detail(@PathVariable UUID id){
    return ResponseEntity.ok(userDeckService.detailById(id));
  }

  @GetMapping()
  public ResponseEntity<UserDeckListResponse> list(){
    return ResponseEntity.ok(userDeckService.findAll());
  }
}