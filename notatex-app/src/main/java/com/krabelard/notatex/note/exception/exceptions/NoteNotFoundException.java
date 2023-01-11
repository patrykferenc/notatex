package com.krabelard.notatex.note.exception.exceptions;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(UUID noteUuid) {
        super(String.format("Note with given UUID does not exist [%s]", noteUuid));
    }

}
