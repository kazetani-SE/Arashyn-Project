package com.arashi.edu.arashynbe.features.auth.client;

import com.arashi.edu.arashynbe.features.auth.dto.request.LoginRequest;
import com.arashi.edu.arashynbe.features.auth.dto.request.RegisterRequest;
import com.arashi.edu.arashynbe.features.auth.dto.response.SupabaseLoginResponse;
import com.arashi.edu.arashynbe.features.auth.dto.response.SupabaseRegisterResponse;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class SupabaseAuthClientImpl implements SupabaseAuthClient {

  private final RestClient supabaseRestClient;

  @Override
  public SupabaseRegisterResponse register(RegisterRequest request) {

    try{
      return supabaseRestClient
              .post()
              .uri("/auth/v1/signup")
              .body(request)
              .retrieve()
              .body(SupabaseRegisterResponse.class);
    } catch (HttpClientErrorException.UnprocessableContent ex) {

      String body = ex.getResponseBodyAsString();

      if (body.contains("user_already_exists")) {
        throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
      }

      throw ex;
    }
  }

  @Override
  public SupabaseLoginResponse login(LoginRequest request) {

    try {
      return supabaseRestClient
              .post()
              .uri("/auth/v1/token?grant_type=password")
              .body(request)
              .retrieve()
              .body(SupabaseLoginResponse.class);

    } catch (HttpClientErrorException.BadRequest ex) {

      String body = ex.getResponseBodyAsString();

      if (body.contains("invalid_credentials")) {
        throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
      }

      throw ex;
    }

  }

}