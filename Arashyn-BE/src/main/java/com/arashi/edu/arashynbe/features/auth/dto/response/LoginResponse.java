package com.arashi.edu.arashynbe.features.auth.dto.response;

public record LoginResponse(

        String username,

        String avatar,

        String accessToken
) {
}