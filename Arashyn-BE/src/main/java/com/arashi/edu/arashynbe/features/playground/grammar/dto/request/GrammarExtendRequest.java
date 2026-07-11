package com.arashi.edu.arashynbe.features.playground.grammar.dto.request;

import com.arashi.edu.arashynbe.features.playground.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.playground.note.dto.request.NoteCreateRequest;

import java.util.List;

public record GrammarExtendRequest(

        MeaningCreateRequest meanings,

        List<NoteCreateRequest> notes,

        AssignFilterRequest filters

) {
}