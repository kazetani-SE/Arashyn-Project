package com.arashi.edu.arashynbe.features.learning.arrange.service;

import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.CreateArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.SubmitArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.SubmitArrangeResponse;

public interface ArrangeService {

  CreateArrangeResponse createArrangeTest(CreateArrangeRequest request);

  SubmitArrangeResponse submitArrangeAnswers(SubmitArrangeRequest request);
}