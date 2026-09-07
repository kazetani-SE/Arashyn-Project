package com.arashi.edu.arashynbe.features.system.grammar.dto.request;

import com.arashi.edu.arashynbe.features.system.filter.dto.request.AssignFilterRequest;
import com.arashi.edu.arashynbe.features.system.meaning.dto.request.MeaningCreateRequest;
import com.arashi.edu.arashynbe.features.system.note.dto.request.NoteCreateRequest;

import java.util.List;

public record GrammarExtendRequest(

        MeaningCreateRequest meanings,

        List<NoteCreateRequest> notes,

        AssignFilterRequest filters

) {
}