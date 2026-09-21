package com.arashi.edu.arashynbe.features.learning.fillblank.service;

import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.CreateFillBlankTestRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.SubmitFillBlankRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.CreateFillBlankResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.SubmitFillBlankResponse;

public interface FillBlankService {

  CreateFillBlankResponse createFillBlankQuestion(CreateFillBlankTestRequest request);

  SubmitFillBlankResponse submitFillBlankAnswers(SubmitFillBlankRequest request);
}