package com.arashi.edu.arashynbe.features.playground.note.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.Note;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteTransferRefRequest;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.features.playground.note.service.NoteService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.ComponentRepo;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.repository.NoteRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

  private final NoteRepo noteRepo;
  private final GrammarRepo grammarRepo;
  private final AccountRepo accountRepo;
  private final ComponentRepo componentRepo;

  @Override
  public UUID create(
          UUID grammarId,
          NoteCreateRequest request
  ) {

    UUID userId = CurrentUser.getId();

    Grammar grammar = grammarRepo.findById(grammarId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    if (!componentRepo.existsByGrammarIdAndGroupKey(
            grammarId,
            request.groupKey().shortValue())) {
      throw new ApiException(ErrorCode.INVALID_GROUP_KEY);
    }

    Account owner = accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));

    boolean isPublic = grammar.getOwner() != null
            && grammar.getIsPublic()
            && !Boolean.FALSE.equals(request.isPublic());

    Note note = Note.builder()
            .grammar(grammar)
            .owner(owner)
            .content(request.content().trim())
            .groupKey(request.groupKey().shortValue())
            .isPublic(isPublic)
            .build();

    return noteRepo.save(note).getId();
  }

  @Override
  public void createMany(
          Grammar grammar,
          List<NoteCreateRequest> notes
  ) {

    if (notes == null || notes.isEmpty()) {
      return;
    }

    List<Note> entities = new ArrayList<>();

    for (NoteCreateRequest dto : notes) {

      if (dto.content() == null || dto.content().isBlank()) {
        continue;
      }

      if (!componentRepo.existsByGrammarIdAndGroupKey(
              grammar.getId(),
              dto.groupKey().shortValue())) {
        throw new ApiException(ErrorCode.INVALID_GROUP_KEY);
      }

      boolean isPublic = grammar.getOwner() != null
              && grammar.getIsPublic()
              && !Boolean.FALSE.equals(dto.isPublic());

      Note note = Note.builder()
              .grammar(grammar)
              .owner(grammar.getOwner())
              .content(dto.content().trim())
              .groupKey(dto.groupKey().shortValue())
              .isPublic(isPublic)
              .build();

      entities.add(note);
    }

    if (!entities.isEmpty()) {
      noteRepo.saveAll(entities);
    }
  }

  @Override
  @Transactional
  public void transferReference(
          NoteTransferRefRequest request
  ) {

    if (!grammarRepo.existsById(request.oldGrammarId())) {
      throw new ApiException(ErrorCode.GRAMMAR_NOT_FOUND);
    }

    if (!grammarRepo.existsById(request.newGrammarId())) {
      throw new ApiException(ErrorCode.GRAMMAR_NOT_FOUND);
    }

    if (!accountRepo.existsById(request.creatorId())) {
      throw new ApiException(ErrorCode.USER_NOT_FOUND);
    }

    noteRepo.updateGrammarReferenceExceptCreator(
            request.oldGrammarId(),
            request.newGrammarId(),
            request.creatorId()
    );
  }
}