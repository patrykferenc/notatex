package com.krabelard.notatex.note.exception.exceptions;

public class NoteIOException extends RuntimeException {

    public NoteIOException(String name) {
        super(String.format("Unexpected error while processing note [%s]", name));
    }

}
