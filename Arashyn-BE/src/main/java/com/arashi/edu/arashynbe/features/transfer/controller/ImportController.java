package com.arashi.edu.arashynbe.features.transfer.controller;

import com.arashi.edu.arashynbe.features.system.grammar.service.GrammarService;
import com.arashi.edu.arashynbe.features.transfer.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
public class ImportController {

  private final ExcelService excelService;
  private final GrammarService grammarService;

  @PostMapping(
          value = "/cvs",
          consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<Void> exportTemplate(MultipartFile file) throws IOException {

    var grammars = excelService.importData(file);

    grammarService.createMultipleGrammar(grammars);

    return ResponseEntity.ok().build();

  }

}