package com.arashi.edu.arashynbe.features.auth.client;

import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.SupabaseLoginResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.SupabaseRegisterResponse;

public interface SupabaseAuthClient {

  SupabaseRegisterResponse register(RegisterRequest request);

  SupabaseLoginResponse login(LoginRequest request);

  SupabaseLoginResponse refresh(String refreshToken);
}