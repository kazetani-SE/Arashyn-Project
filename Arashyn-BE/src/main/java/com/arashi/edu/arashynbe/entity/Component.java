package com.arashi.edu.arashynbe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "component")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Component {

  @Id
  @UuidGenerator
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "grammar_id", nullable = false)
  private Grammar grammar;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "form_id")
  private Form form;

  @Column(name = "`order`", nullable = false)
  private Integer order;

  @Column(name = "keyword", length = 50)
  private String keyword;

  @Column(name = "optional", nullable = false)
  private Boolean optional;

  @Column(name = "group_key", nullable = false)
  private Short groupKey;
}