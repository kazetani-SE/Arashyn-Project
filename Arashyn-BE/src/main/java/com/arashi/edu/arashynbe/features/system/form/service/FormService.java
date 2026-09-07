package com.arashi.edu.arashynbe.features.system.form.service;

import com.arashi.edu.arashynbe.features.system.form.dto.request.FormCreateRequest;
import com.arashi.edu.arashynbe.features.system.form.dto.response.ListFormResponse;

import java.util.UUID;

public interface FormService {

  UUID create(FormCreateRequest request);

  ListFormResponse findByLanguage(String language);
}