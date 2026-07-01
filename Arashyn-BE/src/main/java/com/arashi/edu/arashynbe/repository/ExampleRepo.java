package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExampleRepo extends JpaRepository<Example, UUID> {

  List<Example> findAllByMeaningId(UUID meaningId);

  void deleteAllByMeaningId(UUID meaningId);

}