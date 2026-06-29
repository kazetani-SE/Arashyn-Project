package com.arashi.edu.arashynbe.features.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupabaseLoginResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("expires_at")
        Long expiresAt,

        @JsonProperty("token_type")
        String tokenType,

        SupabaseUser user
) {

  public record SupabaseUser(
          String id,
          String email
  ) {
  }
}