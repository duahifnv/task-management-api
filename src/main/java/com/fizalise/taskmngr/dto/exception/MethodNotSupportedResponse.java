package com.fizalise.taskmngr.dto.exception;

public record MethodNotSupportedResponse(String message, String[] supportedMethods) {
}
