package com.arashi.edu.arashynbe.features.playground.form.service.impl;

import com.arashi.edu.arashynbe.entity.Form;
import com.arashi.edu.arashynbe.features.playground.form.dto.request.FormCreateRequest;
import com.arashi.edu.arashynbe.features.playground.form.dto.response.ListFormResponse;
import com.arashi.edu.arashynbe.features.playground.form.service.FormService;
import com.arashi.edu.arashynbe.repository.FormRepo;
import com.arashi.edu.arashynbe.shared.exception.ApiException;
import com.arashi.edu.arashynbe.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

  private final FormRepo formRepo;

  @Override
  public UUID create(FormCreateRequest request) {

    boolean exists = formRepo
            .findByNameAndTypeAndLanguage(
                    request.name().trim(),
                    request.type().trim(),
                    request.language().name()
            )
            .isPresent();

    if (exists) {
      throw new ApiException(ErrorCode.FORM_ALREADY_EXISTS);
    }

    Form form = Form.builder()
            .name(request.name().trim())
            .type(request.type().trim())
            .language(request.language().name())
            .build();

    return formRepo.save(form).getId();
  }

  @Override
  public ListFormResponse findByLanguage(String language) {
    if (language == null || language.isBlank()) {
      throw new ApiException(ErrorCode.INVALID_LANGUAGE);
    }

    var forms = formRepo.findByLanguage(language);

    var responses = forms.stream()
            .map(form -> new ListFormResponse.FormResponse(
                    form.getId(),
                    form.getName(),
                    form.getType()
            ))
            .toList();

    return new ListFormResponse(responses);
  }
}