package com.arashi.edu.arashynbe.features.transfer.controller;

import com.arashi.edu.arashynbe.features.transfer.dto.request.ExcelTemplateExportRequest;
import com.arashi.edu.arashynbe.features.transfer.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

  private final ExcelService excelService;

  @PostMapping("/csv")
  public ResponseEntity<byte[]> exportTemplate(ExcelTemplateExportRequest request) throws IOException {
    byte[] file = excelService.exportTemplate(request);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"grammar_entry_template.xlsx\""
            )
            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
            )
            .body(file);
  }

}