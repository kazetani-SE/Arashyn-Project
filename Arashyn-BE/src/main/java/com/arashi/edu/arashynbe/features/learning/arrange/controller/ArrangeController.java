package com.arashi.edu.arashynbe.features.learning.arrange.controller;

import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.CreateArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.request.SubmitArrangeRequest;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.CreateArrangeResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.dto.response.SubmitArrangeResponse;
import com.arashi.edu.arashynbe.features.learning.arrange.service.ArrangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learning/arrange")
@RequiredArgsConstructor
public class ArrangeController {

  private final ArrangeService arrangeService;

  @PostMapping
  public CreateArrangeResponse create(@Valid @RequestBody CreateArrangeRequest request) {
    return arrangeService.createArrangeTest(request);
  }

  @PostMapping("/submit")
  public SubmitArrangeResponse submit(@Valid @RequestBody SubmitArrangeRequest request) {
    return arrangeService.submitArrangeAnswers(request);
  }
}