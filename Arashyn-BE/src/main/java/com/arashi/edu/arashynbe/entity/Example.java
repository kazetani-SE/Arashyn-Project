package com.arashi.edu.arashynbe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "example")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Example {

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meaning_id", nullable = false)
  private Meaning meaning;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private Account owner;

  @Column(name = "sentence", nullable = false, columnDefinition = "TEXT")
  private String sentence;

  @Column(name = "translation", columnDefinition = "TEXT")
  private String translation;

  @Column(name = "note", columnDefinition = "TEXT")
  private String note;

  @Column(name = "group_key", nullable = false)
  private Short groupKey;

  @Column(name = "is_public", nullable = false)
  private Boolean isPublic;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @PrePersist
  void prePersist() {
    createdAt = OffsetDateTime.now();
    updatedAt = createdAt;
    if (groupKey == null) {
      groupKey = 0;
    }
    if (isPublic == null) {
      isPublic = false;
    }
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = OffsetDateTime.now();
  }
}