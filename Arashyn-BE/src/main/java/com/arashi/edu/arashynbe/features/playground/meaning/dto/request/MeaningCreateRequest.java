package com.arashi.edu.arashynbe.features.playground.meaning.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record MeaningCreateRequest(

        @NotEmpty
        List<@Valid MeaningCreate> meanings
) {

        public record MeaningCreate(
                @NotNull
                @Positive
                Integer groupKey,

                @NotNull
                @Valid
                MeaningCreateBase meaning
        ){}

}