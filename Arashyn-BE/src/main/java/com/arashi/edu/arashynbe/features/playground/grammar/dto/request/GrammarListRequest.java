package com.arashi.edu.arashynbe.features.playground.grammar.dto.request;

import com.arashi.edu.arashynbe.shared.enums.GrammarSortBy;
import com.arashi.edu.arashynbe.shared.enums.SortDirection;

import java.util.List;
import java.util.UUID;

public record GrammarListRequest(

        String title,

        UUID owner,

        List<String> keywords,

        List<UUID> filterIds,

        List<UUID> formIds,

        GrammarSortBy sortBy,

        SortDirection direction
) {
}