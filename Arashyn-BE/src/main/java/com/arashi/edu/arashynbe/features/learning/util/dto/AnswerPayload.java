package com.arashi.edu.arashynbe.features.learning.util.dto;

import java.util.UUID;

public record AnswerPayload(
        UUID userGrammarId,
        String answer
) {}