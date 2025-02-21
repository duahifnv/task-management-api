package com.fizalise.taskmngr.dto.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
