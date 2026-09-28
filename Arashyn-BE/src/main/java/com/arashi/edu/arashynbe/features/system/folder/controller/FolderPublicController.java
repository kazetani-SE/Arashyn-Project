package com.arashi.edu.arashynbe.features.system.folder.controller;

import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderDetailResponse;
import com.arashi.edu.arashynbe.features.system.folder.dto.response.FolderListResponse;
import com.arashi.edu.arashynbe.features.system.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public/folder")
@RequiredArgsConstructor
public class FolderPublicController {

  private final FolderService folderService;

  @GetMapping
  public ResponseEntity<FolderListResponse> getFolderList() {
    return ResponseEntity.ok(folderService.listFolders());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FolderDetailResponse> getFolder(@PathVariable UUID id) {
    return ResponseEntity.ok(folderService.findFolderById(id));
  }

}