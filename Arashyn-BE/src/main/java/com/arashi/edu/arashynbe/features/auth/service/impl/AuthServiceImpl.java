package com.arashi.edu.arashynbe.features.auth.service.impl;

import com.arashi.edu.arashynbe.entity.Account;
import com.arashi.edu.arashynbe.features.auth.client.SupabaseAuthClient;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;
import com.arashi.edu.arashynbe.features.auth.service.AuthService;
import com.arashi.edu.arashynbe.repository.AccountRepo;
import com.arashi.edu.arashynbe.shared.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final SupabaseAuthClient supabaseAuthClient;
  private final AccountRepo accountRepository;

  @Override
  public RegisterResponse register(RegisterRequest request) {

    var response = supabaseAuthClient.register(request);

    var account = new Account();

    account.setId(response.user().id());
    account.setUsername(request.userName());
    account.setRole(Role.USER);
    account.setBanned(false);
    account.setCreatedAt(Instant.now());

    accountRepository.save(account);

    return new RegisterResponse(
            "Registration successful. Please verify your email."
    );
  }
}