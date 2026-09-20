package com.arashi.edu.arashynbe.features.transfer.service;

import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.transfer.dto.request.TemplateExportRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {

  byte[] exportTemplate(TemplateExportRequest request) throws IOException;

  void exportData();

  GrammarCreateMultipleRequest importData(MultipartFile file) throws IOException;

}