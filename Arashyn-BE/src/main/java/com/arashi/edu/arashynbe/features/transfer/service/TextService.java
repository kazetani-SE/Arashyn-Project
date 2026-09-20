package com.arashi.edu.arashynbe.features.transfer.service;

import com.arashi.edu.arashynbe.features.transfer.dto.request.TemplateExportRequest;

import java.io.IOException;

public interface TextService {

  byte[] exportTemplate(TemplateExportRequest request) throws IOException;

}