package com.arashi.edu.arashynbe.features.transfer.service.impl;

import com.arashi.edu.arashynbe.features.system.component.dto.request.ComponentCreateRequest;
import com.arashi.edu.arashynbe.features.system.example.dto.request.ExampleCreateRequest;
import com.arashi.edu.arashynbe.features.system.filter.service.SystemFilterService;
import com.arashi.edu.arashynbe.features.system.form.service.FormService;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateMultipleRequest;
import com.arashi.edu.arashynbe.features.system.grammar.dto.request.GrammarCreateRequest;
import com.arashi.edu.arashynbe.features.system.meaning.dto.request.MeaningCreateBase;
import com.arashi.edu.arashynbe.features.system.note.dto.request.NoteCreateRequest;
import com.arashi.edu.arashynbe.features.transfer.dto.IdLabel;
import com.arashi.edu.arashynbe.features.transfer.dto.MeaningKey;
import com.arashi.edu.arashynbe.features.transfer.dto.MeaningRow;
import com.arashi.edu.arashynbe.features.transfer.dto.request.ExcelTemplateExportRequest;
import com.arashi.edu.arashynbe.features.transfer.service.ExcelService;
import com.arashi.edu.arashynbe.shared.enums.Language;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcelServiceImpl implements ExcelService {

  private final FormService formService;
  private final SystemFilterService systemFilterService;

  private static final String EXCEL_TEMPLATE_PATH = "templates/excel/grammar_entry_template.xlsx";

  @Override
  public byte[] exportTemplate(ExcelTemplateExportRequest request) throws IOException {
    String languageCode = request.language().getCode();

    List<IdLabel> forms = formService.findByLanguage(languageCode)
            .forms().stream()
            .map(form -> new IdLabel(form.id().toString(), form.name()))
            .toList();

    List<IdLabel> systemFilters = systemFilterService.listSystemFiltersByLanguage(languageCode)
            .systemFilters().stream()
            .map(filter -> new IdLabel(filter.id().toString(), filter.name()))
            .toList();

    ClassPathResource resource = new ClassPathResource(EXCEL_TEMPLATE_PATH);

    try (InputStream in = resource.getInputStream();
         Workbook wb = WorkbookFactory.create(in);
         ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      injectLookupData(wb, forms, systemFilters);
      populateLanguageCells(wb, languageCode);

      wb.write(out);
      return out.toByteArray();
    }
  }

  @Override
  public void exportData() {
  }

  @Override
  public GrammarCreateMultipleRequest importData(MultipartFile file) throws IOException {
    try (InputStream in = file.getInputStream();
         Workbook wb = WorkbookFactory.create(in)) {

      FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
      DataFormatter formatter = new DataFormatter();
      List<GrammarCreateRequest> result = new ArrayList<>();

      for (Sheet sheet : wb) {
        String sheetName = sheet.getSheetName();
        if ("Guide".equals(sheetName) || "_Lookup".equals(sheetName)) {
          continue;
        }
        result.add(parseGrammarSheet(sheet, evaluator, formatter));
      }

      return new GrammarCreateMultipleRequest(result);
    }
  }

  private void populateLanguageCells(Workbook wb, String languageCode) {
    for (int i = 2; i < wb.getNumberOfSheets(); i++) {
      Sheet sheet = wb.getSheetAt(i);
      Row row = sheet.getRow(5);
      if (row == null) {
        row = sheet.createRow(5);
      }
      Cell languageCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
      languageCell.setCellValue(languageCode);
    }
  }

  public void injectLookupData(Workbook wb, List<IdLabel> forms, List<IdLabel> filters) {
    Sheet lookup = wb.getSheet("_Lookup");

    int row = 1; // row 2 (index 1), row 0 is header
    for (IdLabel f : forms) {
      Row r = lookup.getRow(row) != null ? lookup.getRow(row) : lookup.createRow(row);
      r.createCell(0).setCellValue(f.id());    // col A = FormId
      r.createCell(1).setCellValue(f.label());  // col B = FormLabel
      row++;
    }

    row = 1;
    for (IdLabel x : filters) {
      Row r = lookup.getRow(row) != null ? lookup.getRow(row) : lookup.createRow(row);
      r.createCell(3).setCellValue(x.id());     // col D = FilterId
      r.createCell(4).setCellValue(x.label());   // col E = FilterLabel
      row++;
    }
  }

  private GrammarCreateRequest parseGrammarSheet(Sheet sheet, FormulaEvaluator evaluator, DataFormatter formatter) {
    String title = str(sheet.getRow(4), 1, evaluator, formatter);
    String languageCode = str(sheet.getRow(5), 1, evaluator, formatter);
    boolean isPublic = Boolean.parseBoolean(str(sheet.getRow(6), 1));

    Map<Integer, List<ComponentCreateRequest>> componentsByGroup = new LinkedHashMap<>();
    Map<Integer, List<MeaningRow>> meaningsByGroup = new LinkedHashMap<>();
    Map<MeaningKey, List<ExampleCreateRequest>> examplesByMeaning = new HashMap<>();

    // Components: Row 12+ (POI index 11+)
    for (int r = 11; r <= 39; r++) {
      Row row = sheet.getRow(r);
      String groupKeyValue = str(row, 0);
      if (isBlank(groupKeyValue)) continue;

      int groupKey = Integer.parseInt(groupKeyValue);
      ComponentCreateRequest component = new ComponentCreateRequest(
              Integer.parseInt(str(row, 1)),
              uuidOrNull(str(row, 3, evaluator, formatter)),
              str(row, 4, evaluator, formatter),
              Boolean.parseBoolean(str(row, 5, evaluator, formatter))
      );

      componentsByGroup.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(component);
    }

    // Meanings: Row 45+ (POI index 44+)
    for (int r = 44; r <= 72; r++) {
      Row row = sheet.getRow(r);
      String groupKeyValue = str(row, 0);
      if (isBlank(groupKeyValue)) continue;

      int groupKey = Integer.parseInt(groupKeyValue);
      int meaningOrder = Integer.parseInt(str(row, 1));
      MeaningRow meaning = new MeaningRow(meaningOrder, str(row, 2), Boolean.parseBoolean(str(row, 3)));

      meaningsByGroup.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(meaning);
    }

    // Examples: Row 78+ (POI index 77+)
    for (int r = 77; r <= 115; r++) {
      Row row = sheet.getRow(r);
      String groupKeyValue = str(row, 0);
      if (isBlank(groupKeyValue)) continue;

      int groupKey = Integer.parseInt(groupKeyValue);
      int meaningOrder = Integer.parseInt(str(row, 1));
      ExampleCreateRequest example = new ExampleCreateRequest(
              str(row, 2),
              str(row, 3),
              str(row, 4),
              Boolean.parseBoolean(str(row, 5))
      );

      MeaningKey key = new MeaningKey(groupKey, meaningOrder);
      examplesByMeaning.computeIfAbsent(key, k -> new ArrayList<>()).add(example);
    }

    // Build Groups
    Map<Integer, GrammarCreateRequest.Group> groups = new LinkedHashMap<>();

    for (Map.Entry<Integer, List<ComponentCreateRequest>> entry : componentsByGroup.entrySet()) {
      int groupKey = entry.getKey();
      groups.computeIfAbsent(groupKey, k -> new GrammarCreateRequest.Group(k, new ArrayList<>(), new ArrayList<>()));
      groups.get(groupKey).components().addAll(entry.getValue());
    }

    for (Map.Entry<Integer, List<MeaningRow>> entry : meaningsByGroup.entrySet()) {
      int groupKey = entry.getKey();
      GrammarCreateRequest.Group group = groups.computeIfAbsent(
              groupKey, k -> new GrammarCreateRequest.Group(k, new ArrayList<>(), new ArrayList<>())
      );

      for (MeaningRow meaningRow : entry.getValue()) {
        MeaningKey key = new MeaningKey(groupKey, meaningRow.order());
        List<ExampleCreateRequest> examples = examplesByMeaning.getOrDefault(key, new ArrayList<>());
        MeaningCreateBase meaning = new MeaningCreateBase(meaningRow.content(), meaningRow.isPublic(), examples);
        group.meanings().add(meaning);
      }
    }

    // Notes: Row 121+ (POI index 120+)
    List<NoteCreateRequest> notes = new ArrayList<>();
    for (int r = 120; r <= 138; r++) {
      Row row = sheet.getRow(r);
      String groupKeyValue = str(row, 0);
      if (isBlank(groupKeyValue)) continue;

      notes.add(new NoteCreateRequest(
              str(row, 1),
              Boolean.parseBoolean(str(row, 2)),
              Integer.parseInt(groupKeyValue)
      ));
    }

    // FilterIds: Row 144+ (POI index 143+)
    List<UUID> filterIds = new ArrayList<>();
    for (int r = 143; r <= 161; r++) {
      String filterId = str(sheet.getRow(r), 1, evaluator, formatter);

      if (!isBlank(filterId)) {
        filterIds.add(UUID.fromString(filterId));
      }
    }

    return new GrammarCreateRequest(
            title,
            Language.valueOf(languageCode),
            isPublic,
            new ArrayList<>(groups.values()),
            notes,
            filterIds
    );
  }

  private static String str(Row row, int col) {
    if (row == null) return null;
    Cell cell = row.getCell(col);
    if (cell == null) return null;
    return new DataFormatter().formatCellValue(cell).trim();
  }

  private static String str(Row row, int col, FormulaEvaluator evaluator, DataFormatter formatter) {
    if (row == null) return null;
    Cell cell = row.getCell(col);
    if (cell == null) return null;
    return formatter.formatCellValue(cell, evaluator).trim();
  }

  private static boolean isBlank(String value) {
    return value == null || value.isBlank();
  }

  private static UUID uuidOrNull(String value) {
    try {
      return isBlank(value) ? null : UUID.fromString(value);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}