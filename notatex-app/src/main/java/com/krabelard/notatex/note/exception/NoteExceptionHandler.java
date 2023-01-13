package com.krabelard.notatex.note.exception;

import com.krabelard.notatex.note.controller.NoteCompilerController;
import com.krabelard.notatex.note.controller.NoteCrudController;
import com.krabelard.notatex.note.exception.exceptions.NoteConflictException;
import com.krabelard.notatex.note.exception.exceptions.NoteIOException;
import com.krabelard.notatex.note.exception.exceptions.NoteNotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {NoteCrudController.class, NoteCompilerController.class})
public class NoteExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionDTO handle404(Exception e) {
        return ex(e.getMessage());
    }

    @ExceptionHandler(NoteConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionDTO handle409(Exception e) {
        return ex(e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class, NoteIOException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiExceptionDTO handle500(Exception e) {
        return ex(e.getMessage());
    }

    private ApiExceptionDTO ex(String message) {
        return ApiExceptionDTO.builder()
                .message(message)
                .timestamp(ZonedDateTime.now())
                .build();
    }

    @Data
    @Builder
    private static class ApiExceptionDTO {

        private final String message;
        private final ZonedDateTime timestamp;

    }

}
