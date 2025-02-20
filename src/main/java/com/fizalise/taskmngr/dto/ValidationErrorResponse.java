package com.fizalise.taskmngr.dto;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
