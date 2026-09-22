package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.Grammar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class GrammarRepoImpl implements GrammarRepoCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<Grammar> searchGrammars(
          String query,
          String language,
          List<UUID> filterIds,
          List<UUID> formIds,
          boolean isKeyword,
          Pageable pageable
  ) {
    List<String> keywords = splitKeywords(query);
    List<Object[]> params = new ArrayList<>();

    StringBuilder where = new StringBuilder(
            " WHERE g.owner_id IS NOT NULL AND g.language = :language "
    );
    params.add(new Object[]{"language", language});

    if (isKeyword) {
      for (int i = 0; i < keywords.size(); i++) {
        String p = "componentKw" + i;
        where.append("""
                 AND EXISTS (
                     SELECT 1 FROM component c
                     WHERE c.grammar_id = g.id
                       AND LOWER(c.keyword) LIKE LOWER(CONCAT('%', :""").append(p).append(", '%'))\n");
        where.append(")\n");
        params.add(new Object[]{p, keywords.get(i)});
      }
    } else {
      for (int i = 0; i < keywords.size(); i++) {
        String p = "meaningKw" + i;
        where.append("""
                 AND EXISTS (
                     SELECT 1 FROM meaning m
                     WHERE m.grammar_id = g.id
                       AND LOWER(m.content) LIKE LOWER(CONCAT('%', :""").append(p).append(", '%'))\n");
        where.append(")\n");
        params.add(new Object[]{p, keywords.get(i)});
      }
    }

    if (filterIds != null && !filterIds.isEmpty()) {
      for (int i = 0; i < filterIds.size(); i++) {
        String p = "filterId" + i;
        where.append("""
                 AND EXISTS (
                     SELECT 1 FROM grammar_filter gf
                     WHERE gf.grammar_id = g.id AND gf.filter_id = :""").append(p).append(")\n");
        params.add(new Object[]{p, filterIds.get(i)});
      }
    }

    if (formIds != null && !formIds.isEmpty()) {
      for (int i = 0; i < formIds.size(); i++) {
        String p = "formId" + i;
        where.append("""
                 AND EXISTS (
                     SELECT 1 FROM component c
                     WHERE c.grammar_id = g.id AND c.form_id = :""").append(p).append(")\n");
        params.add(new Object[]{p, formIds.get(i)});
      }
    }

    String selectSql = "SELECT g.* FROM grammar g" + where + " ORDER BY g.updated_at DESC";
    String countSql = "SELECT COUNT(*) FROM grammar g" + where;

    Query selectQuery = entityManager.createNativeQuery(selectSql, Grammar.class);
    Query countQuery = entityManager.createNativeQuery(countSql);

    for (Object[] param : params) {
      selectQuery.setParameter((String) param[0], param[1]);
      countQuery.setParameter((String) param[0], param[1]);
    }

    selectQuery.setFirstResult((int) pageable.getOffset());
    selectQuery.setMaxResults(pageable.getPageSize());

    @SuppressWarnings("unchecked")
    List<Grammar> content = selectQuery.getResultList();
    long total = ((Number) countQuery.getSingleResult()).longValue();

    return new PageImpl<>(content, pageable, total);
  }

  private List<String> splitKeywords(String query) {
    List<String> keywords = new ArrayList<>();
    if (query == null || query.isBlank()) {
      return keywords;
    }
    String trimmed = query.trim();
    if (trimmed.contains("+")) {
      for (String part : trimmed.split("\\+", 2)) {
        String p = part.trim();
        if (!p.isEmpty()) keywords.add(p);
      }
    } else {
      keywords.add(trimmed);
    }
    return keywords;
  }
}