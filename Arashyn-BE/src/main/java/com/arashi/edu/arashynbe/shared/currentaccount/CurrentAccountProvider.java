package com.arashi.edu.arashynbe.shared.currentaccount;

import com.arashi.edu.arashynbe.config.security.CurrentUser;
import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.repository.auth.AccountRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentAccountProvider {

  private final AccountRepo accountRepo;

  public Account get() {
    UUID userId = CurrentUser.getId();

    return accountRepo.findById(userId)
            .orElseThrow(() ->
                    new ApiException(ErrorCode.USER_NOT_FOUND));
  }
}