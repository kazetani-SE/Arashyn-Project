package com.arashi.edu.arashynbe.features.auth.dto;

public record AuthResult(

        String username,

        String avatar,

        String accessToken,

        String refreshToken

) {
}