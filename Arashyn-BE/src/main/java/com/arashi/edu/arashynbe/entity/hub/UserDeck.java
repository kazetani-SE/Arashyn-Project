package com.arashi.edu.arashynbe.entity.hub;

import com.arashi.edu.arashynbe.entity.auth.Account;
import com.arashi.edu.arashynbe.entity.system.Deck;
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
@Table(
        name = "user_deck",
        uniqueConstraints = @UniqueConstraint(columnNames = {"deck_id", "user_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeck {

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "deck_id", nullable = false)
  private Deck deck;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private Account user;

  @Column(name = "name", length = 200, nullable = false)
  private String name;

  @Column(name = "proficiency", nullable = false)
  private Short proficiency;

  @Column(name = "last_open_at")
  private OffsetDateTime lastOpenAt;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_folder_deck",
          joinColumns = @JoinColumn(name = "user_deck_id"),
          inverseJoinColumns = @JoinColumn(name = "user_folder_id")
  )
  @Builder.Default
  private Set<UserFolder> userFolders = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_deck_grammar",
          joinColumns = @JoinColumn(name = "user_deck_id"),
          inverseJoinColumns = @JoinColumn(name = "user_grammar_id")
  )
  @Builder.Default
  private Set<UserGrammar> userGrammars = new HashSet<>();

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