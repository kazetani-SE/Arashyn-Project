package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.entity.AccountSession;
import com.arashi.edu.arashynbe.features.auth.client.SupabaseAuthClient;
import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RefreshRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.LoginResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.features.auth.support.SupabaseJwtDecoder;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.repository.AccountSessionRepo;
import com.arashi.edu.arashynbe.shared.enums.Role;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final SupabaseAuthClient supabaseAuthClient;
  private final AccountRepo accountRepository;
  private final AccountSessionRepo accountSessionRepo;
  private final SupabaseJwtDecoder supabaseJwtDecoder;

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
  public LoginResponse login(LoginRequest request) {
    var response = supabaseAuthClient.login(request);

    var accountId = UUID.fromString(response.user().id());

    var account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    var session = accountSessionRepo.findById(account.getId())
            .orElse(AccountSession.builder()
                    .account(account)
                    .build());

    session.setRefreshToken(response.refreshToken());

    accountSessionRepo.save(session);

    return new LoginResponse(account.getUsername(), account.getAvatar(), response.accessToken());
  }

  @Override
  public LoginResponse refresh(RefreshRequest request) {

    var claims = supabaseJwtDecoder.decodeAllowExpired(request.accessToken());
    var accountId = UUID.fromString(claims.getSubject());

    var account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    var session = accountSessionRepo.findById(accountId)
            .orElseThrow(() -> new ApiException(ErrorCode.SESSION_NOT_FOUND));

    var response = supabaseAuthClient.refresh(session.getRefreshToken());

    session.setRefreshToken(response.refreshToken());
    accountSessionRepo.save(session);

    return new LoginResponse(account.getUsername(), account.getAvatar(), response.accessToken());
  }
}