package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.features.playground.example.dto.request.ExampleCreateRequest;
import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarExtendRequest;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarModifyService;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.service.MeaningService;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.features.playground.note.service.NoteService;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GrammarModifyServiceImpl implements GrammarModifyService {

  private final MeaningService meaningService;
  private final NoteService noteService;
  private final SystemFilterService systemFilterService;

  @Override
  @Transactional
  public void extendGrammar(UUID grammarId, GrammarExtendRequest request) {

    boolean hasMeanings =
            request.meanings() != null
                    && request.meanings().meanings() != null
                    && !request.meanings().meanings().isEmpty();

    boolean hasNotes =
            request.notes() != null
                    && !request.notes().isEmpty();

    boolean hasFilters =
            request.filters() != null
                    && request.filters().filterIds() != null
                    && !request.filters().filterIds().isEmpty();

    if (!hasMeanings && !hasNotes && !hasFilters) {
      throw new ApiException(ErrorCode.EMPTY_EXTEND_REQUEST);
    }

    if (hasMeanings) {
      meaningService.create(
              grammarId,
              request.meanings()
      );
    }

    if (hasNotes) {
      for (var note : request.notes()) {
        noteService.create(
                grammarId,
                note
        );
      }
    }

    if (hasFilters) {
      systemFilterService.assignFilters(
              grammarId,
              request.filters()
      );
    }
  }

  @Override
  public void updateGrammar(UUID grammarId, @Valid GrammarCreateRequest request) {

  }
}