package com.assessment.categorymanagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("CAT-ERR-001", "Something went wrong"),
    INVALID_PARENT("CAT-ERR-002", "Invalid parentId"),
    INVALID_CATEGORY_ID("CAT-ERR-003", "Invalid categoryId");

    private String errorCode;
    private String errorDesc;
}
