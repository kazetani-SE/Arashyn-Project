package com.arashi.edu.arashynbe.features.playground.note.service;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;

import java.util.List;
import java.util.UUID;

public interface NoteService {

  UUID create(
          UUID grammarId,
          NoteCreateRequest request
  );

  void createMany(
          Grammar grammar,
          List<NoteCreateRequest> notes
  );
}