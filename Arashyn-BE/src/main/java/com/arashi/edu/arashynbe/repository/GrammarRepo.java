package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GrammarRepo extends JpaRepository<Grammar, UUID> {

  List<Grammar> findByLanguage(String language);

  List<Grammar> findByOwnerId(UUID ownerId);

  List<Grammar> findByIsPublicTrue();

  List<Grammar> findByLanguageAndIsPublicTrue(String language);
}