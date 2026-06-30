package com.arashi.edu.arashynbe.repository;

import com.arashi.edu.arashynbe.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormRepo extends JpaRepository<Form, UUID> {

  Optional<Form> findByNameAndTypeAndLanguage(String name, String type, String language);

  List<Form> findByLanguage(String language);

  List<Form> findByType(String type);
}