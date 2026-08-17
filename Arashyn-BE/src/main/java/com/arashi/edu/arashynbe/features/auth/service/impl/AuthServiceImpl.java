package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.AccountSession;
import com.arashi.edu.arashynbe.features.auth.client.SupabaseAuthClient;
import com.arashi.edu.arashynbe.features.auth.dto.AuthResult;
import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.AccountSessionRepo;
import com.arashi.edu.arashynbe.shared.enums.Role;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final SupabaseAuthClient supabaseAuthClient;
  private final AccountRepo accountRepository;
  private final AccountSessionRepo accountSessionRepo;
  public static final Duration REFRESH_TOKEN_MAX_AGE = Duration.ofDays(30);

  @Override
  public RegisterResponse register(RegisterRequest request) {

    var response = supabaseAuthClient.register(request);

    var account = new Account();

    account.setId(response.user().id());
    account.setUsername(request.userName());
    account.setEmail(request.email());
    account.setRole(Role.USER);
    account.setBanned(false);
    account.setCreatedAt(Instant.now());

    accountRepository.save(account);

    return new RegisterResponse(
            "Registration successful."
    );
  }

  @Override
  public AuthResult login(LoginRequest request) {
    var response = supabaseAuthClient.login(request);
    var accountId = UUID.fromString(response.user().id());

    var account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    var session = accountSessionRepo.findById(accountId)
            .orElse(AccountSession.builder().account(account).build());

    session.setRefreshToken(response.refreshToken());
    accountSessionRepo.save(session);

    return new AuthResult(account.getUsername(), account.getAvatar(),
            response.accessToken(), response.refreshToken());
  }

  @Override
  @Transactional
  public AuthResult refresh(String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new ApiException(ErrorCode.SESSION_NOT_FOUND);
    }

    var session = accountSessionRepo.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new ApiException(ErrorCode.SESSION_NOT_FOUND));

    if (Duration.between(session.getCreatedAt(), Instant.now())
            .compareTo(REFRESH_TOKEN_MAX_AGE) > 0) {
      accountSessionRepo.delete(session);
      throw new ApiException(ErrorCode.SESSION_EXPIRED);
    }

    var account = session.getAccount();
    var response = supabaseAuthClient.refresh(refreshToken);

    session.setRefreshToken(response.refreshToken()); 
    accountSessionRepo.save(session);

    return new AuthResult(account.getUsername(), account.getAvatar(),
            response.accessToken(), response.refreshToken());
  }

  @Override
  @Transactional
  public void logout(String refreshToken) {
    if (refreshToken == null || refreshToken.isBlank()) {
      return;
    }

    System.out.println(refreshToken);

    accountSessionRepo.findByRefreshToken(refreshToken)
            .ifPresent(accountSessionRepo::delete);
  }
}