package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComponentRepo extends JpaRepository<Component, UUID> {

  List<Component> findByGrammarIdOrderByGroupKeyAscOrderAsc(UUID grammarId);

  List<Component> findByFormId(UUID formId);

  boolean existsByGrammarIdAndGroupKey(
          UUID grammarId,
          Short groupKey
  );

  List<Component> findByGrammarIdInAndGroupKeyOrderByGrammarIdAscOrderAsc(
          List<UUID> grammarIds,
          Short groupKey
  );
}