package com.arashi.edu.arashynbe.features.learning.proficiency.service;

import java.util.UUID;

public interface ProficiencyService {

  void update(UUID userGrammarId, int changes);

}