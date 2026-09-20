package com.arashi.edu.arashynbe.features.learning.fillblank.service.impl;

import com.arashi.edu.arashynbe.features.hub.userdeck.dto.response.UserDeckDetailResponse;
import com.arashi.edu.arashynbe.features.hub.userdeck.service.UserDeckService;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarListResponse.UserGrammarSummarisedResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.CreateFillBlankTestRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.SubmitFillBlankRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.CreateFillBlankResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.CreateFillBlankResponse.Question;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.CreateFillBlankResponse.Question.QuestionComponent;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.SubmitFillBlankResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.service.FillBlankService;
import com.arashi.edu.arashynbe.features.learning.util.AnswerCryptoUtil;
import com.arashi.edu.arashynbe.features.system.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.system.form.dto.response.ListFormResponse;
import com.arashi.edu.arashynbe.features.system.form.service.FormService;
import com.arashi.edu.arashynbe.features.system.meaning.dto.response.GrammarMeaningSummaryResponse;
import com.arashi.edu.arashynbe.shared.enums.Difficulty;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillBlankServiceImpl implements FillBlankService {

  private static final int MIN_CHOICE_SIZE = 5;

  private final UserDeckService userDeckService;
  private final FormService formService;
  private final AnswerCryptoUtil answerCryptoUtil;

  private record GrammarGroupPair(UserGrammarSummarisedResponse grammar, Short groupKey) {}

  @Override
  public CreateFillBlankResponse createFillBlankQuestion(CreateFillBlankTestRequest request) {

    UserDeckDetailResponse deck = userDeckService.detailById(request.userDeckId());

    if (deck == null) {
      throw new ApiException(ErrorCode.USER_DECK_NOT_FOUND);
    }

    if (deck.grammars() == null || deck.grammars().items() == null) {
      throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
    }

    List<UserGrammarSummarisedResponse> grammars = deck.grammars().items();

    if (!request.hasForm() && !request.hasKeyword()) {
      throw new ApiException(ErrorCode.INVALID_FILL_BLANK_REQUEST);
    }

    List<GrammarGroupPair> pairs = buildGrammarGroupPairs(grammars);

    if (pairs.isEmpty() || pairs.size() < request.numOfQuestion()) {
      throw new ApiException(ErrorCode.NOT_ENOUGH_GRAMMAR_FOR_TEST);
    }

    Collections.shuffle(pairs);
    List<GrammarGroupPair> selectedPairs = pairs.subList(0, request.numOfQuestion());

    List<String> deckForms = collectDeckForms(grammars);
    List<String> formChoicePool = buildFormChoicePool(deckForms, deck.language().name());

    int blanksPerQuestion = resolveBlankCount(request.difficulty());
    List<Question> questions = new ArrayList<>();

    for (GrammarGroupPair pair : selectedPairs) {
      questions.add(buildQuestion(pair, blanksPerQuestion, formChoicePool, request));
    }

    return new CreateFillBlankResponse(questions);
  }

  @Override
  public SubmitFillBlankResponse submitFillBlankAnswers(SubmitFillBlankRequest request) {

    if (request == null || request.answers() == null || request.answers().isEmpty()) {
      throw new ApiException(ErrorCode.SUBMIT_ANSWERS_EMPTY);
    }

    List<SubmitFillBlankResponse.AnswerResult> results = new ArrayList<>();
    int correctCount = 0;

    for (SubmitFillBlankRequest.SubmitAnswer answer : request.answers()) {
      AnswerCryptoUtil.VerifyResult result = answerCryptoUtil.verifyAndReveal(
              answer.iv(),
              answer.answerToken(),
              answer.userAnswer()
      );

      if (result.correct()) {
        correctCount++;
      }

      results.add(new SubmitFillBlankResponse.AnswerResult(answer.iv(), result.correct(), result.correctAnswer()));
    }

    return new SubmitFillBlankResponse(results, correctCount, request.answers().size());
  }

  private int resolveBlankCount(Difficulty difficulty) {
    if (difficulty == null) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }
    return switch (difficulty) {
      case EASY -> 1;
      case MEDIUM -> 2;
      case HARD -> 3;
    };
  }

  private List<GrammarGroupPair> buildGrammarGroupPairs(List<UserGrammarSummarisedResponse> grammars) {
    List<GrammarGroupPair> pairs = new ArrayList<>();

    for (UserGrammarSummarisedResponse grammar : grammars) {
      if (grammar.components() == null || grammar.components().isEmpty()) {
        throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
      }

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

  private List<String> collectDeckForms(List<UserGrammarSummarisedResponse> grammars) {
    Set<String> forms = new LinkedHashSet<>();

    for (UserGrammarSummarisedResponse grammar : grammars) {
      if (grammar.components() == null) {
        continue;
      }
      for (GrammarComponentSummaryResponse component : grammar.components()) {
        if (component.keyword() == null && component.form() != null && !component.form().isBlank()) {
          forms.add(component.form());
        }
      }
    }

    return new ArrayList<>(forms);
  }

  private List<String> buildFormChoicePool(List<String> deckForms, String language) {
    Set<String> pool = new LinkedHashSet<>(deckForms);

    if (pool.size() < MIN_CHOICE_SIZE) {
      ListFormResponse systemForms = formService.findByLanguage(language);

      if (systemForms == null || systemForms.forms() == null) {
        throw new ApiException(ErrorCode.FORM_NOT_FOUND);
      }

      Iterator<ListFormResponse.FormResponse> iterator = systemForms.forms().iterator();
      while (pool.size() < MIN_CHOICE_SIZE && iterator.hasNext()) {
        pool.add(iterator.next().name());
      }

      if (pool.size() < MIN_CHOICE_SIZE) {
        throw new ApiException(ErrorCode.FORM_NOT_FOUND);
      }
    }

    return new ArrayList<>(pool);
  }

  private Question buildQuestion(
          GrammarGroupPair pair,
          int blanksPerQuestion,
          List<String> formChoicePool,
          CreateFillBlankTestRequest request
  ) {
    UserGrammarSummarisedResponse grammar = pair.grammar();
    Short groupKey = pair.groupKey();

    List<GrammarComponentSummaryResponse> groupComponents = grammar.components().stream()
            .filter(component -> groupKey.equals(component.groupKey()))
            .sorted(Comparator.comparing(GrammarComponentSummaryResponse::order))
            .toList();

    if (groupComponents.isEmpty()) {
      throw new ApiException(ErrorCode.GRAMMAR_COMPONENT_EMPTY);
    }

    List<GrammarComponentSummaryResponse> blankCandidates = groupComponents.stream()
            .filter(component -> isBlankable(component, request))
            .collect(Collectors.toCollection(ArrayList::new));

    if (blankCandidates.isEmpty()) {
      throw new ApiException(ErrorCode.NO_BLANK_CANDIDATE_AVAILABLE);
    }

    Collections.shuffle(blankCandidates);
    int numBlanks = Math.min(blanksPerQuestion, blankCandidates.size());

    Set<Integer> blankedOrders = new LinkedHashSet<>();
    for (int i = 0; i < numBlanks; i++) {
      blankedOrders.add(blankCandidates.get(i).order());
    }

    List<QuestionComponent> questionComponents = new ArrayList<>();
    for (GrammarComponentSummaryResponse component : groupComponents) {
      questionComponents.add(toQuestionComponent(component, blankedOrders.contains(component.order()), formChoicePool));
    }

    String meaning = grammar.meanings().stream()
            .filter(m -> groupKey.equals(m.groupKey()))
            .map(GrammarMeaningSummaryResponse::content)
            .findFirst()
            .orElseThrow(() -> new ApiException(ErrorCode.GRAMMAR_MEANING_NOT_FOUND));

    return new Question(questionComponents, meaning);
  }

  private boolean isBlankable(GrammarComponentSummaryResponse component, CreateFillBlankTestRequest request) {
    boolean isKeyword = component.keyword() != null;
    return (isKeyword && request.hasKeyword()) || (!isKeyword && request.hasForm());
  }

  private QuestionComponent toQuestionComponent(
          GrammarComponentSummaryResponse component,
          boolean isBlanked,
          List<String> formChoicePool
  ) {
    boolean isKeyword = component.keyword() != null;

    if (!isBlanked) {
      String content = isKeyword ? component.keyword() : component.form();
      return new QuestionComponent(content, null, null, null, component.order());
    }

    String realAnswer = isKeyword ? component.keyword() : component.form();
    String[] encrypted = answerCryptoUtil.encrypt(realAnswer); // [iv, token]

    List<String> choices = isKeyword ? List.of() : formChoicePool;

    return new QuestionComponent("", encrypted[1], encrypted[0], choices, component.order());
  }
}