package com.arashi.edu.arashynbe.features.system.grammar.dto.response;

import com.arashi.edu.arashynbe.features.system.component.dto.response.GrammarComponentResponse;
import com.arashi.edu.arashynbe.features.system.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.system.meaning.dto.response.GrammarMeaningResponse;
import com.arashi.edu.arashynbe.features.system.note.dto.response.GrammarNoteResponse;
import com.arashi.edu.arashynbe.shared.enums.Language;

import java.util.List;
import java.util.UUID;

public record GrammarDetailResponse(

        UUID id,

        String title,

        Language language,

        boolean isPublic,

        UUID ownerId,

        String ownerName,

        List<Group> groups,

        List<GrammarNoteResponse> notes,

        List<GrammarFilterResponse> filters

) {

  public record Group(

          Integer groupKey,

          List<GrammarComponentResponse> components,

          List<GrammarMeaningResponse> meanings

  ) {
  }
}