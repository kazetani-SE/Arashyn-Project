package com.arashi.edu.arashynbe.features.playground.grammar.service;

import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.features.playground.component.dto.response.GrammarComponentResponse;
import com.arashi.edu.arashynbe.features.playground.example.dto.response.GrammarExampleResponse;
import com.arashi.edu.arashynbe.features.playground.filter.dto.response.GrammarFilterResponse;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.playground.meaning.dto.response.GrammarMeaningResponse;
import com.arashi.edu.arashynbe.features.playground.note.dto.response.GrammarNoteResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GrammarReadService {

  Grammar requireGrammar(UUID grammarId);

  List<GrammarComponentResponse> getComponents(UUID grammarId);

  List<GrammarMeaningResponse> getMeanings(UUID grammarId);

  Map<UUID, List<GrammarExampleResponse>> getExamples(List<UUID> meaningIds);

  List<GrammarNoteResponse> getNotes(UUID grammarId);

  List<GrammarFilterResponse> getFilters(UUID grammarId);

  List<GrammarDetailResponse.Group> buildGroups(
          List<GrammarComponentResponse> components,
          List<GrammarMeaningResponse> meanings
  );
}