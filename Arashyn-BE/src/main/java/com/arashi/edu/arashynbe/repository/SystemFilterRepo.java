package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.SystemFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemFilterRepo extends JpaRepository<SystemFilter, UUID> {

  Optional<SystemFilter> findByNameAndLanguage(
          String name,
          String language
  );

  List<SystemFilter> findAllByIdIn(
          List<UUID> ids
  );
}