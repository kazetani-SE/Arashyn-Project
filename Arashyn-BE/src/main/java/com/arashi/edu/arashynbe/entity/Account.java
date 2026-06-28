package com.arashi.edu.arashynbe.entity;

import com.arashi.edu.arashynbe.shared.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account")
public class Account {

  @Id
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(columnDefinition = "TEXT")
  private String avatar;

  @Column(nullable = false, columnDefinition = "account_role")
  private Role role;

  @Column(name = "is_banned", nullable = false)
  private boolean banned;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}