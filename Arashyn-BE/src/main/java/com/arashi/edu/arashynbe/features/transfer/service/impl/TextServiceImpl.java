package com.arashi.edu.arashynbe.features.transfer.service.impl;

import com.arashi.edu.arashynbe.features.system.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.system.form.service.FormService;
import com.arashi.edu.arashynbe.features.transfer.dto.IdLabel;
import com.arashi.edu.arashynbe.features.transfer.dto.request.TemplateExportRequest;
import com.arashi.edu.arashynbe.features.transfer.service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TextServiceImpl implements TextService {

  private final FormService formService;
  private final SystemFilterService systemFilterService;

  private static final String TEXT_TEMPLATE_PATH = "templates/text/grammar_entry_template.txt";

  @Override
  public byte[] exportTemplate(TemplateExportRequest request) throws IOException {
    String languageCode = request.language().getCode();

    List<IdLabel> forms = formService.findByLanguage(languageCode)
            .forms()
            .stream()
            .map(form -> new IdLabel(form.id().toString(), form.name()))
            .toList();

    List<IdLabel> systemFilters = systemFilterService.listSystemFiltersByLanguage(languageCode)
            .systemFilters()
            .stream()
            .map(filter -> new IdLabel(filter.id().toString(), filter.name()))
            .toList();

    ClassPathResource resource = new ClassPathResource(TEXT_TEMPLATE_PATH);

    String template = resource.getContentAsString(StandardCharsets.UTF_8);

    String formsContent = formatIdLabels(forms);
    String filtersContent = formatIdLabels(systemFilters);

    template = replaceSection(template, "FORMS", formsContent);
    template = replaceSection(template, "FILTERS", filtersContent);

    return template.getBytes(StandardCharsets.UTF_8);
  }

  private String replaceSection(
          String template,
          String section,
          String content
  ) {
    String regex = "(?s)(" + Pattern.quote(section) + "\\R).*?(?=\\R[A-Z][A-Z ]*\\R|\\z)";

    return template.replaceFirst(
            regex,
            Matcher.quoteReplacement(section + "\n\n" + content + "\n")
    );
  }

  private String formatIdLabels(List<IdLabel> items) {
    return items.stream()
            .map(item -> item.label() + " | " + item.id())
            .collect(Collectors.joining("\n"));
  }
}