package com.fizalise.taskmngr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnableToStartExecutionException extends ResponseStatusException {
    public UnableToStartExecutionException() {
        super(HttpStatus.BAD_REQUEST, "Невозможно начать задачу: задача либо уже запущена, либо завершена");
    }
}
