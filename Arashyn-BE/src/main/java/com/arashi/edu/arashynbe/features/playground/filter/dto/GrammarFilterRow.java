package com.arashi.edu.arashynbe.features.playground.filter.dto;

import java.util.UUID;

public record GrammarFilterRow(

        UUID id,

        UUID grammarId,

        String name

) {
}