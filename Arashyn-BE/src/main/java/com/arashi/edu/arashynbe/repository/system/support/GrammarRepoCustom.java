package com.arashi.edu.arashynbe.repository.system.support;

import com.arashi.edu.arashynbe.entity.system.Grammar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GrammarRepoCustom {

  Page<Grammar> searchGrammars(
          String query,
          String language,
          List<UUID> filterIds,
          List<UUID> formIds,
          boolean isKeyword,
          Pageable pageable
  );
}