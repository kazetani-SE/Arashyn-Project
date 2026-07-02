package com.arashi.edu.arashynbe.features.playground.meaning.dto.request;

import com.arashi.edu.arashynbe.features.playground.example.dto.request.ExampleCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MeaningCreateBase(
        @NotBlank
        @Size(max = 5000)
        String content,

        List<@Valid ExampleCreateRequest> examples
) {
}