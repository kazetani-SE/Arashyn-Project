package com.arashi.edu.arashynbe.features.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "pending_registrations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingRegistration {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false, unique = true, length = 255)
  private String email;

  @Column(name = "verification_code", nullable = false, length = 255)
  private String verificationCode; // BCrypt hash of OTP

  // Stored as raw JSON string; service layer (de)serializes with ObjectMapper
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(nullable = false, columnDefinition = "jsonb")
  private String payload;

  @Column(name = "expires_at", nullable = false)
  private OffsetDateTime expiresAt;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private OffsetDateTime createdAt;
}