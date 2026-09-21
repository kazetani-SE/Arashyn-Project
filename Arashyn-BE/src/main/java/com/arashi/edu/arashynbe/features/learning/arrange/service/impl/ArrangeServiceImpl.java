package com.arashi.edu.arashynbe.features.learning.arrange.service.impl;

import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.service.UserDeckService;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarListResponse.UserGrammarSummarisedResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.CreateArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.SubmitArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse.Question;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse.Question.BlankComponent;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse.PoolItem;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.SubmitArrangeResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.service.ArrangeService;
import com.arashi.edu.arashynbe.features.learning.util.AnswerCryptoUtil;
import com.arashi.edu.arashynbe.features.system.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.system.meaning.dto.response.GrammarMeaningSummaryResponse;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArrangeServiceImpl implements ArrangeService {

  private final UserDeckService userDeckService;
  private final AnswerCryptoUtil answerCryptoUtil;

  private record GrammarGroupPair(UserGrammarSummarisedResponse grammar, Short groupKey) {}

  @Override
  public CreateArrangeResponse createArrangeTest(CreateArrangeRequest request) {

    UserDeckDetailResponse deck = userDeckService.detailById(request.userDeckId());

    if (deck == null) {
      throw new ApiException(ErrorCode.USER_DECK_NOT_FOUND);
    }

    if (deck.grammars() == null || deck.grammars().items() == null) {
      throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
    }

    List<UserGrammarSummarisedResponse> grammars = deck.grammars().items();

    if (grammars.isEmpty() || grammars.size() < request.numOfQuestion()) {
      throw new ApiException(ErrorCode.NOT_ENOUGH_GRAMMAR_FOR_TEST);
    }

    Map<String, String> contentToItemId = new LinkedHashMap<>();

    for (UserGrammarSummarisedResponse grammar : grammars) {
      if (grammar.components() == null || grammar.components().isEmpty()) {
        throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
      }
      for (GrammarComponentSummaryResponse component : grammar.components()) {
        registerPoolItem(component, contentToItemId);
      }
    }

    List<PoolItem> itemPool = contentToItemId.entrySet().stream()
            .map(e -> new PoolItem(e.getValue(), stripPrefix(e.getKey())))
            .toList();

    List<GrammarGroupPair> pairs = buildGrammarGroupPairs(grammars);

    if (pairs.isEmpty() || pairs.size() < request.numOfQuestion()) {
      throw new ApiException(ErrorCode.NOT_ENOUGH_GRAMMAR_FOR_TEST);
    }

    Collections.shuffle(pairs);
    List<GrammarGroupPair> selectedPairs = pairs.subList(0, request.numOfQuestion());

    List<Question> questions = new ArrayList<>();
    for (GrammarGroupPair pair : selectedPairs) {
      questions.add(buildQuestion(pair));
    }

    return new CreateArrangeResponse(questions, itemPool);
  }

  @Override
  public SubmitArrangeResponse submitArrangeAnswers(SubmitArrangeRequest request) {

    if (request == null || request.answers() == null || request.answers().isEmpty()) {
      throw new ApiException(ErrorCode.SUBMIT_ANSWERS_EMPTY);
    }

    List<SubmitArrangeResponse.AnswerResult> results = new ArrayList<>();
    int correctCount = 0;

    for (SubmitArrangeRequest.SubmitAnswer answer : request.answers()) {
      AnswerCryptoUtil.VerifyResult result = answerCryptoUtil.verifyAndReveal(
              answer.iv(),
              answer.answerToken(),
              answer.userAnswer()
      );

      if (result.correct()) {
        correctCount++;
      }

      results.add(new SubmitArrangeResponse.AnswerResult(answer.iv(), result.correct(), result.correctAnswer()));
    }

    return new SubmitArrangeResponse(results, correctCount, request.answers().size());
  }

  private void registerPoolItem(GrammarComponentSummaryResponse component, Map<String, String> contentToItemId) {
    boolean isKeyword = component.keyword() != null;

    String content = isKeyword ? component.keyword() : component.form();

    if (content == null || content.isBlank()) {
      throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
    }

    String dedupeKey = (isKeyword ? "K:" : "F:") + content;

    contentToItemId.computeIfAbsent(dedupeKey, k -> UUID.randomUUID().toString());
  }

  private String stripPrefix(String dedupeKey) {
    return dedupeKey.substring(2);
  }

  private List<GrammarGroupPair> buildGrammarGroupPairs(List<UserGrammarSummarisedResponse> grammars) {
    List<GrammarGroupPair> pairs = new ArrayList<>();

    for (UserGrammarSummarisedResponse grammar : grammars) {
      Set<Short> groupKeys = new LinkedHashSet<>();
      for (GrammarComponentSummaryResponse component : grammar.components()) {
        groupKeys.add(component.groupKey());
      }
      for (Short groupKey : groupKeys) {
        pairs.add(new GrammarGroupPair(grammar, groupKey));
      }
    }

    return pairs;
  }

  private Question buildQuestion(GrammarGroupPair pair) {
    UserGrammarSummarisedResponse grammar = pair.grammar();
    Short groupKey = pair.groupKey();

    List<GrammarComponentSummaryResponse> groupComponents = grammar.components().stream()
            .filter(component -> groupKey.equals(component.groupKey()))
            .sorted(Comparator.comparing(GrammarComponentSummaryResponse::order))
            .toList();

    if (groupComponents.isEmpty()) {
      throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
    }

    List<BlankComponent> blanks = new ArrayList<>();
    for (GrammarComponentSummaryResponse component : groupComponents) {
      boolean isKeyword = component.keyword() != null;
      String realAnswer = isKeyword ? component.keyword() : component.form();

      String[] encrypted = answerCryptoUtil.encrypt(realAnswer); // [iv, token]
      blanks.add(new BlankComponent(encrypted[1], encrypted[0], component.order()));
    }

    String meaning = grammar.meanings().stream()
            .filter(m -> groupKey.equals(m.groupKey()))
            .map(GrammarMeaningSummaryResponse::content)
            .findFirst()
            .orElseThrow(() -> new ApiException(ErrorCode.GRAMMAR_MEANING_NOT_FOUND));

    return new Question(blanks, meaning);
  }
}