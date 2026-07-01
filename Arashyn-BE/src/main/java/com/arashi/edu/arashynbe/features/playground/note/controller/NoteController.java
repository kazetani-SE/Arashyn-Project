package com.arashi.edu.arashynbe.features.playground.note.controller;

import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.features.playground.note.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/grammar/{grammarId}/notes")
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;

  @PostMapping
  public ResponseEntity<Void> create(

          @PathVariable
          UUID grammarId,

          @Valid
          @RequestBody
          NoteCreateRequest request

  ) {

    noteService.create(
            grammarId,
            request
    );

    return ResponseEntity.noContent().build();
  }
}