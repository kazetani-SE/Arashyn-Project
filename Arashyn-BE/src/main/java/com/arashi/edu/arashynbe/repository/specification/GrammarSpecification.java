package com.arashi.edu.arashynbe.repository.specification;

import com.arashi.edu.arashynbe.entity.Component;
import com.arashi.edu.arashynbe.entity.Grammar;
import com.arashi.edu.arashynbe.entity.support.GrammarFilter;
import com.arashi.edu.arashynbe.features.playground.grammar.dto.request.GrammarListRequest;
import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public final class GrammarSpecification {

  private GrammarSpecification() {
  }

  public static Specification<Grammar> build(
          GrammarListRequest request
  ) {

    Specification<Grammar> spec = isPublic();

    if (request.title() != null && !request.title().isBlank()) {
      spec = spec.and(titleContains(request.title()));
    }

    if (request.owner() != null) {
      spec = spec.and(ownerIs(request.owner()));
    }

    if (request.filterIds() != null && !request.filterIds().isEmpty()) {
      spec = spec.and(hasFilters(request.filterIds()));
    }

    if (request.formIds() != null && !request.formIds().isEmpty()) {
      spec = spec.and(hasForms(request.formIds()));
    }

    if (request.keywords() != null && !request.keywords().isEmpty()) {
      spec = spec.and(hasKeywords(request.keywords()));
    }

    if (request.language() != null) {
      spec = spec.and(languageIs(request.language()));
    }

    return spec;
  }

  public static Specification<Grammar> isPublic() {
    return (root, query, cb) ->
            cb.isTrue(root.get("isPublic"));
  }

  public static Specification<Grammar> titleContains(String title) {
    return (root, query, cb) ->
            cb.like(
                    cb.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
  }

  public static Specification<Grammar> ownerIs(UUID ownerId) {
    return (root, query, cb) ->
            cb.equal(
                    root.get("owner").get("id"),
                    ownerId
            );
  }

  public static Specification<Grammar> hasFilters(
          List<UUID> filterIds
  ) {
    return (root, query, cb) -> {

      Subquery<UUID> subQuery = query.subquery(UUID.class);

      Root<GrammarFilter> grammarFilter =
              subQuery.from(GrammarFilter.class);

      subQuery.select(
              grammarFilter.get("grammar").get("id")
      );

      subQuery.where(
              grammarFilter.get("filter").get("id").in(filterIds)
      );

      return root.get("id").in(subQuery);
    };
  }

  public static Specification<Grammar> hasForms(
          List<UUID> formIds
  ) {
    return (root, query, cb) -> {

      Subquery<UUID> subQuery = query.subquery(UUID.class);

      Root<Component> component = subQuery.from(Component.class);

      subQuery.select(
              component.get("grammar").get("id")
      );

      subQuery.where(
              component.get("form").get("id").in(formIds)
      );

      return root.get("id").in(subQuery);
    };
  }

  public static Specification<Grammar> hasKeywords(
          List<String> keywords
  ) {
    return (root, query, cb) -> {

      Subquery<UUID> subQuery = query.subquery(UUID.class);

      Root<Component> component = subQuery.from(Component.class);

      subQuery.select(
              component.get("grammar").get("id")
      );

      subQuery.where(
              component.get("keyword").in(keywords)
      );

      return root.get("id").in(subQuery);
    };
  }

  public static Specification<Grammar> languageIs(
          Language language
  ) {
    return (root, query, cb) ->
            cb.equal(
                    root.get("language"),
                    language.getCode()
            );
  }
}