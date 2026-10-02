package com.arashi.edu.arashynbe.features.system.folder.controller;

import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderCreateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.request.FolderUpdateRequest;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderCheckUpdateResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderIdResponse;
import com.arashi.edu.arashynbe.features.system.folder.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/protected/folder")
@RequiredArgsConstructor
public class FolderProtectedController {

  private final FolderService folderService;

  @PostMapping
  ResponseEntity<FolderIdResponse> create(@RequestBody @Valid FolderCreateRequest request) {
    return ResponseEntity.ok(folderService.createFolder(request));
  }

  @PutMapping
  ResponseEntity<FolderIdResponse> update(@RequestBody @Valid FolderUpdateRequest request) {
    return ResponseEntity.ok(folderService.updateFolder(request));
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id) {
    folderService.deleteFolder(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/check-update/{user_folder_id}")
  ResponseEntity<FolderCheckUpdateResponse> checkUpdate(@PathVariable UUID user_folder_id) {
    return ResponseEntity.ok(folderService.checkFolderUpdate(user_folder_id));
  }
}