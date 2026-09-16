package com.arashi.edu.arashynbe.features.hub.usergrammar.service.impl;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.hub.UserDeck;
import com.arashi.edu.arashynbe.entity.hub.UserGrammar;
import com.arashi.edu.arashynbe.entity.system.Grammar;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarCreateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckMultipleRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarDuplicateCheckRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.request.UserGrammarUpdateRequest;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDetailResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckMultipleResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarDuplicateCheckResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarIdResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.dto.response.UserGrammarListResponse;
import com.arashi.edu.arashynbe.features.hub.usergrammar.service.UserGrammarService;
import com.arashi.edu.arashynbe.features.system.component.dto.response.GrammarComponentSummaryResponse;
import com.arashi.edu.arashynbe.features.system.grammar.dto.response.GrammarDetailResponse;
import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarReadService;
import com.arashi.edu.arashynbe.features.system.meaning.dto.response.GrammarMeaningSummaryResponse;
import com.arashi.edu.arashynbe.repository.hub.UserDeckRepo;
import com.arashi.edu.arashynbe.repository.hub.UserGrammarRepo;
import com.arashi.edu.arashynbe.repository.system.GrammarRepo;
import com.arashi.edu.arashynbe.shared.currentaccount.CurrentAccountProvider;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserGrammarServiceImpl implements UserGrammarService {

  private final GrammarRepo grammarRepo;
  private final UserGrammarRepo userGrammarRepo;
  private final UserDeckRepo userDeckRepo;

  private final GrammarReadService grammarReadService;

  private final CurrentAccountProvider currentAccountProvider;

  @Override
  public UserGrammarIdResponse create(UserGrammarCreateRequest request) {
    UserGrammar userGrammar = createEntity(request, currentAccountProvider.get());
    if (userGrammar == null) {
      throw new ApiException(ErrorCode.INVALID_REQUEST);
    }
    return new UserGrammarIdResponse(userGrammar.getId());
  }

  @Override
  public void createMultiple(UserGrammarCreateMultipleRequest request) {
    Account user = currentAccountProvider.get();

    request.userGrammarCreateRequestsList()
            .forEach(req -> createEntity(req, user));
  }

  @Override
  @Transactional(readOnly = true)
  public UserGrammarDuplicateCheckResponse duplicateCheck(
          UserGrammarDuplicateCheckRequest request
  ) {
    Account user = currentAccountProvider.get();

    return userGrammarRepo
            .findFirstByGrammarIdAndUserId(
                    request.id(),
                    user.getId()
            )
            .map(userGrammar -> new UserGrammarDuplicateCheckResponse(
                    request.id(),
                    true,
                    userGrammar.getId()
            ))
            .orElseGet(() -> new UserGrammarDuplicateCheckResponse(
                    request.id(),
                    false,
                    null
            ));
  }

  @Override
  @Transactional(readOnly = true)
  public UserGrammarDuplicateCheckMultipleResponse duplicateCheckMultiple(
          UserGrammarDuplicateCheckMultipleRequest request
  ) {
    List<UserGrammarDuplicateCheckResponse> results = request.duplicateCheckRequestList().stream()
            .map(this::duplicateCheck)
            .toList();

    return new UserGrammarDuplicateCheckMultipleResponse(results);
  }

  @Transactional
  @Override
  public UserGrammarIdResponse update(UserGrammarUpdateRequest request) {
    Account user = currentAccountProvider.get();

    UserGrammar userGrammar = userGrammarRepo.findByIdAndUserId(request.userGrammarId(), user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    if (request.name() != null && !request.name().isBlank()) {
      userGrammar.setName(request.name().trim());
    }

    UUID currentUserDeckId = request.currentUserDeckId();
    UUID newUserDeckId = request.newUserDeckId();

    if (currentUserDeckId == null && newUserDeckId == null) {
      return new UserGrammarIdResponse(userGrammar.getId());
    }

    if (currentUserDeckId == null) {
      UserDeck newUserDeck = userDeckRepo.findByIdAndUserId(newUserDeckId, user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

      newUserDeck.getUserGrammars().add(userGrammar);
      userDeckRepo.save(newUserDeck);
      return new UserGrammarIdResponse(userGrammar.getId());
    }

    boolean exists = userDeckRepo.existsByIdAndUserIdAndUserGrammarsId(
            currentUserDeckId, user.getId(), userGrammar.getId());

    if (!exists) {
      throw new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND);
    }

    UserDeck currentUserDeck = userDeckRepo.findByIdAndUserId(currentUserDeckId, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    if (newUserDeckId == null) {
      currentUserDeck.getUserGrammars().removeIf(g -> g.getId().equals(userGrammar.getId()));
      userDeckRepo.save(currentUserDeck);
      return new UserGrammarIdResponse(userGrammar.getId());
    }

    if (currentUserDeckId.equals(newUserDeckId)) {
      return new UserGrammarIdResponse(userGrammar.getId());
    }

    UserDeck newUserDeck = userDeckRepo.findByIdAndUserId(newUserDeckId, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    currentUserDeck.getUserGrammars().removeIf(g -> g.getId().equals(userGrammar.getId()));
    newUserDeck.getUserGrammars().add(userGrammar);

    userDeckRepo.save(currentUserDeck);
    userDeckRepo.save(newUserDeck);

    return new UserGrammarIdResponse(userGrammar.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public UserGrammarListResponse findAll() {
    Account user = currentAccountProvider.get();

    List<UserGrammar> userGrammars = userGrammarRepo.findAllByUserId(user.getId());

    return toListResponse(userGrammars);
  }

  @Override
  @Transactional(readOnly = true)
  public UserGrammarListResponse findAllByUserDeckId(UUID id) {
    Account user = currentAccountProvider.get();

    List<UserGrammar> userGrammars = userGrammarRepo.findUserGrammarsByDeckIdAndUserId(id, user.getId());

    return toListResponse(userGrammars);
  }

  @Override
  @Transactional
  public UserGrammarDetailResponse findById(UUID id) {
    Account user = currentAccountProvider.get();

    UserGrammar userGrammar = userGrammarRepo.findByIdAndUserId(
            id,
            user.getId()
    ).orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    userGrammar.setLastReviewAt(OffsetDateTime.now());

    GrammarDetailResponse detail = grammarReadService.getDetail(
            userGrammar.getGrammar().getId()
    );

    return new UserGrammarDetailResponse(
            userGrammar.getId(),
            detail.id(),
            userGrammar.getName() != null ? userGrammar.getName() : detail.title(),
            detail.language(),
            detail.groups(),
            detail.notes(),
            detail.filters(),
            userGrammar.getProficiency(),
            userGrammar.getLastReviewAt()
    );
  }

  @Override
  public void delete(UUID id) {
    Account user = currentAccountProvider.get();

    UserGrammar userGrammar = userGrammarRepo.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

    userGrammarRepo.delete(userGrammar);
  }

  @Override
  public void deleteRedundant() {
    UUID userId = currentAccountProvider.get().getId();

    List<UUID> redundantIds = userGrammarRepo.findRedundantGrammarIds(userId);

    if (!redundantIds.isEmpty()) {
      userGrammarRepo.deleteAllByIdInBatch(redundantIds);
    }
  }

  private UserGrammar createEntity(UserGrammarCreateRequest request, Account user) {
    UUID grammarId = request.grammarId();
    UUID sourceUserGrammarId = request.sourceUserGrammarId();

    if (grammarId == null && sourceUserGrammarId == null) {
      return null;
    }

    if (grammarId == null) {
      UserGrammar source = userGrammarRepo
              .findByIdAndUserId(sourceUserGrammarId, user.getId())
              .orElseThrow(() -> new ApiException(ErrorCode.USER_DECK_NOT_FOUND));

      if (request.userDeckId() != null) {
        UserDeck userDeck = userDeckRepo
                .findByIdAndUserId(request.userDeckId(), user.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

        userDeck.getUserGrammars().add(source);
        userDeckRepo.save(userDeck);
      }

      return source;
    }

    Grammar grammar = grammarRepo.findById(request.grammarId())
            .orElseThrow(() -> new ApiException(ErrorCode.GRAMMAR_NOT_FOUND));

    UserGrammar.UserGrammarBuilder builder = UserGrammar.builder()
            .grammar(grammar)
            .user(user)
            .name(
                    request.name() != null && !request.name().isBlank()
                            ? request.name().trim()
                            : grammar.getTitle()
            )
            .proficiency((short) 0)
            .lastReviewAt(null);

    if (request.sourceUserGrammarId() != null) {

      UserGrammar source = userGrammarRepo
              .findByIdAndUserId(
                      request.sourceUserGrammarId(),
                      user.getId()
              )
              .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

      builder
              .proficiency(source.getProficiency())
              .lastReviewAt(source.getLastReviewAt());
    }

    UserGrammar userGrammar = userGrammarRepo.save(builder.build());

    if (request.userDeckId() != null) {

      UserDeck userDeck = userDeckRepo
              .findByIdAndUserId(
                      request.userDeckId(),
                      user.getId()
              )
              .orElseThrow(() -> new ApiException(ErrorCode.USER_GRAMMAR_NOT_FOUND));

      userDeck.getUserGrammars().add(userGrammar);
      userDeckRepo.save(userDeck);
    }

    return userGrammar;
  }

  private UserGrammarListResponse toListResponse(List<UserGrammar> userGrammars) {
    List<UserGrammarListResponse.UserGrammarSummarisedResponse> result = userGrammars.stream()
            .map(userGrammar -> {
              Grammar grammar = userGrammar.getGrammar();
              String title = userGrammar.getName() != null ? userGrammar.getName() : grammar.getTitle();

              GrammarDetailResponse detail = grammarReadService.getDetail(grammar.getId());

              List<GrammarComponentSummaryResponse> components = detail.groups().stream()
                      .flatMap(group -> group.components().stream())
                      .map(component -> new GrammarComponentSummaryResponse(
                              component.groupKey(),
                              component.order(),
                              component.keyword(),
                              component.form()
                      ))
                      .toList();

              List<GrammarMeaningSummaryResponse> meanings = detail.groups().stream()
                      .flatMap(group -> group.meanings().stream())
                      .map(meaning -> new GrammarMeaningSummaryResponse(
                              meaning.content()
                      ))
                      .toList();

              return new UserGrammarListResponse.UserGrammarSummarisedResponse(
                      userGrammar.getId(),
                      title,
                      components,
                      meanings,
                      detail.filters(),
                      userGrammar.getProficiency(),
                      userGrammar.getLastReviewAt()
              );
            })
            .toList();

    return new UserGrammarListResponse(result, (long) result.size());
  }
}