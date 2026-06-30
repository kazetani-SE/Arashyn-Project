package com.arashi.edu.arashynbe.features.playground.form.service;

import com.arashi.edu.arashynbe.features.playground.form.dto.request.FormCreateRequest;

import java.util.UUID;

public interface FormService {

  UUID create(FormCreateRequest request);

}