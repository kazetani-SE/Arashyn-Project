package com.arashi.edu.arashynbe.features.system.filter.dto;

import java.util.UUID;

public record GrammarFilterRow(

        UUID id,

        UUID grammarId,

        String name

) {
}