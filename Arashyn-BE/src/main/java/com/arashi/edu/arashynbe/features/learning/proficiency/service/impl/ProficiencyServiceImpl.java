package com.arashi.edu.arashynbe.features.learning.proficiency.service.impl;

import com.arashi.edu.arashynbe.entity.hub.UserGrammar;
import com.arashi.edu.arashynbe.features.learning.proficiency.service.ProficiencyService;
import com.arashi.edu.arashynbe.repository.hub.UserGrammarRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.enums.Proficiency;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProficiencyServiceImpl implements ProficiencyService {

  private final UserGrammarRepo userGrammarRepo;

  private final CurrentAccountProvider currentAccountProvider;

  @Override
  public void update(UUID userGrammarId, int changes) {
    if (changes != 1 && changes != -1) {
      throw new ApiException(ErrorCode.INVALID_CHANGE_VALUE);
    }

    var account = currentAccountProvider.get();

    UserGrammar userGrammar = userGrammarRepo
            .findByIdAndUserId(userGrammarId, account.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    int current = userGrammar.getProficiency() == null
            ? Proficiency.minValue()
            : userGrammar.getProficiency();

    int updated = Math.clamp(current + changes, Proficiency.minValue(), Proficiency.maxValue());

    userGrammar.setProficiency((short) updated);
    userGrammar.setLastReviewAt(OffsetDateTime.now());
  }
}