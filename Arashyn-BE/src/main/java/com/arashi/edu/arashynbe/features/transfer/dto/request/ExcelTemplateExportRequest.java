package com.arashi.edu.arashynbe.features.transfer.dto.request;

import com.arashi.edu.arashynbe.shared.enums.Language;
import jakarta.validation.constraints.NotNull;

public record ExcelTemplateExportRequest(

        @NotNull
        Language language

) {
}