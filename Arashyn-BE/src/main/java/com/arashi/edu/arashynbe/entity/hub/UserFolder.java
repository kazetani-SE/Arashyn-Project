package com.arashi.edu.arashynbe.entity.hub;

import com.arashi.edu.arashynbe.entity.auth.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_folder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFolder {

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "folder_id")
  private UUID folderId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Account user;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "synced_version", nullable = false)
  @Builder.Default
  private Integer syncedVersion = 0;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_folder_hierarchy",
          joinColumns = @JoinColumn(name = "parent_id"),
          inverseJoinColumns = @JoinColumn(name = "child_id")
  )
  @Builder.Default
  private Set<UserFolder> children = new HashSet<>();

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @PrePersist
  void prePersist() {
    createdAt = OffsetDateTime.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = OffsetDateTime.now();
  }
}