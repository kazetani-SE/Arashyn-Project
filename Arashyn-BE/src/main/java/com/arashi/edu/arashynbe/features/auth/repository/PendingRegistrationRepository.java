package com.arashi.edu.arashynbe.features.auth.repository;

import com.arashi.edu.arashynbe.features.auth.entity.PendingRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PendingRegistrationRepository extends JpaRepository<PendingRegistration, UUID> {

  Optional<PendingRegistration> findByEmail(String email);

  boolean existsByEmail(String email);

  void deleteByEmail(String email);

  // Used by a scheduled cleanup job
  @Modifying
  @Query("delete from PendingRegistration p where p.expiresAt < :now")
  int deleteAllExpired(@Param("now") OffsetDateTime now);
}