package com.arashi.edu.arashynbe.features.system.grammar.service.impl;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarDeleteService;
import com.arashi.edu.arashynbe.repository.system.GrammarRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import com.arashi.edu.arashynbe.shared.ownership.OwnerShip;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GrammarDeleteServiceImpl implements GrammarDeleteService {

  private final GrammarRepo grammarRepo;

  private final OwnerShip ownerShip;

  @Override
  @Transactional
  public void deleteGrammar(UUID grammarId) {
    Grammar grammar = ownerShip.requireOwnership(
            grammarId,
            grammarRepo,
            ErrorCode.GRAMMAR_NOT_FOUND
    );

    int deleted = grammarRepo.softDelete(grammar.getId());
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