package com.sisecofi.admindevengados.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class PdfFileValidator implements ConstraintValidator<PdfFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {
            return Boolean.FALSE;
        }
        return Objects.equals(file.getContentType(), "application/pdf");
    }
}
