package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Meaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeaningRepo extends JpaRepository<Meaning, UUID> {

  List<Meaning> findByGrammarIdOrderByGroupKeyAsc(UUID grammarId);

  List<Meaning> findByGrammarIdInAndGroupKeyOrderByGrammarIdAsc(
          List<UUID> grammarIds,
          Short groupKey
  );
}