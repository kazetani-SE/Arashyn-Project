package com.arashi.edu.arashynbe.features.auth.service;

import com.arashi.edu.arashynbe.features.auth.dto.AuthResult;
import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.RegisterResponse;

public interface AuthService {

  RegisterResponse register(RegisterRequest request);

  AuthResult login(LoginRequest request);

  AuthResult refresh(String refreshToken);

  void logout(String refreshToken);
}