package com.arashi.edu.arashynbe.features.playground.filter.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record AssignFilterRequest(

        @NotEmpty
        List<UUID> filterIds

) {
}