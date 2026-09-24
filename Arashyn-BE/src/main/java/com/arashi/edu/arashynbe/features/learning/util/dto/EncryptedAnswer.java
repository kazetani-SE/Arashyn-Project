package com.arashi.edu.arashynbe.features.learning.util.dto;

public record EncryptedAnswer(

        String iv,

        String cipherText
){}