package com.arashi.edu.arashynbe.entity.system;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.system.support.FolderHierarchy;
import com.arashi.edu.arashynbe.shared.ownership.OwnedEntity;
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
@Table(name = "folder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Folder implements OwnedEntity{

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private Account owner;

  @Column(name = "is_public", nullable = false)
  private Boolean isPublic;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  @Builder.Default
  private Set<FolderHierarchy> childLinks = new HashSet<>();

  @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
  @Builder.Default
  private Set<FolderHierarchy> parentLinks = new HashSet<>();

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