package com.fizalise.taskmngr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnableToStopExecutionException extends ResponseStatusException {
    public UnableToStopExecutionException() {
        super(HttpStatus.BAD_REQUEST, "Невозможно остановить задачу: " +
                "задача либо еще не запушена либо уже завершена");
    }
}
