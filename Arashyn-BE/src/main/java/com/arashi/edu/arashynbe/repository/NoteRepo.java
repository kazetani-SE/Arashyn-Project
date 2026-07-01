package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepo extends JpaRepository<Note, UUID> {

  List<Note> findAllByGrammarId(UUID grammarId);

  void deleteAllByGrammarId(UUID grammarId);

}