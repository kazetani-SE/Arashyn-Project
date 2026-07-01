package com.arashi.edu.arashynbe.features.playground.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExampleCreateRequest(

        @NotBlank
        @Size(max = 5000)
        String sentence,

        @Size(max = 5000)
        String translation,

        @Size(max = 5000)
        String note

) {
}