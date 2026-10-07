package com.arashi.edu.arashynbe.features.hub.userfolder.controller;

import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCloneRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderCreateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.request.UserFolderUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderIdResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.dto.response.UserFolderListResponse;
import com.arashi.edu.arashynbe.features.hub.userfolder.service.UserFolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user_folder")
@RequiredArgsConstructor
public class UserFolderController {

  private final UserFolderService userFolderService;

  // C
  @PostMapping("/check_duplicate")
  public ResponseEntity<UserFolderDuplicateCheckResponse> checkDuplicate(@Valid @RequestBody UserFolderDuplicateCheckRequest request) {
    return ResponseEntity.ok(userFolderService.checkDuplicate(request));
  }

  @PostMapping
  public ResponseEntity<UserFolderIdResponse> create(@Valid @RequestBody UserFolderCreateRequest request) {
    return ResponseEntity.ok(userFolderService.create(request));
  }

  @PostMapping("/clone")
  public ResponseEntity<UserFolderIdResponse> clone(@Valid @RequestBody UserFolderCloneRequest request) {
    return ResponseEntity.ok(userFolderService.clone(request));
  }

  // U
  @PutMapping
  public ResponseEntity<UserFolderIdResponse> update(@Valid @RequestBody UserFolderUpdateRequest request) {
    return ResponseEntity.ok(userFolderService.update(request));
  }

  // D
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userFolderService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // R
  @GetMapping("/root")
  public ResponseEntity<UserFolderListResponse> root() {
    return ResponseEntity.ok(userFolderService.findRoot());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserFolderDetailResponse> detail(@PathVariable UUID id) {
    return ResponseEntity.ok(userFolderService.detailById(id));
  }

  @GetMapping
  public ResponseEntity<UserFolderListResponse> list() {
    return ResponseEntity.ok(userFolderService.findAll());
  }
}