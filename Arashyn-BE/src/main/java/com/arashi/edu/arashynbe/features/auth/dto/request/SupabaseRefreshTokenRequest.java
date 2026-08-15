package com.arashi.edu.arashynbe.features.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SupabaseRefreshTokenRequest(
        @JsonProperty("refresh_token") String refreshToken
) {}