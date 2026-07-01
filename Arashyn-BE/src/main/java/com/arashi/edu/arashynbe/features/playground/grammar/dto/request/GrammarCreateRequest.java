package com.arashi.edu.arashynbe.features.playground.grammar.dto.request;

import com.arashi.edu.arashynbe.features.playground.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateBase;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record GrammarCreateRequest(

        @NotBlank
        @Size(min = 1, max = 50)
        String title,

        @NotNull
        Language language,

        @NotNull
        boolean isPublic,

        @NotEmpty
        List<@Valid Group> groups,

        List<@Valid NoteCreateRequest> notes,

        List<UUID> filterIds

) {

        public record Group(

                @NotNull
                @Positive
                Integer groupKey,

                @NotEmpty
                List<@Valid ComponentCreateRequest> components,

                @Valid
                List<MeaningCreateBase> meanings

        ) {
        }
}