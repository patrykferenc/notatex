package com.krabelard.notatex.note.exception.exceptions;

public class NoteConflictException extends RuntimeException {

    public NoteConflictException(String name) {
        super(String.format("Note with given name already exists [%s]", name));
    }

}
