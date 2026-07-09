package com.arashi.edu.arashynbe.features.playground.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.grammar.service.GrammarDeleteService;
import com.arashi.edu.arashynbe.repository.GrammarRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GrammarDeleteServiceImpl implements GrammarDeleteService {

  private final GrammarRepo grammarRepo;

  @Override
  @Transactional
  public void deleteGrammar(UUID grammarId) {

    UUID currentUserId = CurrentUser.getId();

    Grammar grammar = grammarRepo.findById(grammarId)
            .orElseThrow(() -> new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    if (grammar.getOwner() == null) {
      throw new ApiException(ErrorCode.GRAMMAR_NOT_FOUND);
    }

    if (!grammar.getOwner().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }

    int deleted = grammarRepo.softDelete(grammarId);
    if (deleted == 0) {
      throw new ApiException(
              ErrorCode.GRAMMAR_DELETE_FAILED
      );
    }
  }

  @Override
  @Transactional
  public void restoreGrammar(UUID grammarId) {

    UUID userId = CurrentUser.getId();

    int updated = grammarRepo.restoreGrammar(
            grammarId,
            userId
    );

    if (updated == 0) {
      throw new ApiException(
              ErrorCode.GRAMMAR_NOT_FOUND
      );
    }
  }
}