package com.arashi.edu.arashynbe.features.playground.note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NoteCreateRequest(

        @NotBlank
        @Size(max = 5000)
        String content,

        @NotNull
        @Positive
        Integer groupKey

) {
}