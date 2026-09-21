package com.arashi.edu.arashynbe.features.learning.fillblank.controller;

import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.CreateFillBlankTestRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.request.SubmitFillBlankRequest;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.CreateFillBlankResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.dto.response.SubmitFillBlankResponse;
import com.arashi.edu.arashynbe.features.learning.fillblank.service.FillBlankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/fill_blank")
@RequiredArgsConstructor
public class FillBlankController {

  private final FillBlankService  fillBlankService;

  @PostMapping
  public ResponseEntity<CreateFillBlankResponse> create(@Valid @RequestBody CreateFillBlankTestRequest request){
    return ResponseEntity.ok(fillBlankService.createFillBlankQuestion(request));
  }

  @PostMapping("/submit")
  public ResponseEntity<SubmitFillBlankResponse> submit(@Valid @RequestBody SubmitFillBlankRequest request){
    return ResponseEntity.ok(fillBlankService.submitFillBlankAnswers(request));
  }
}