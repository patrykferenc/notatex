package com.krabelard.notatex.note.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NoteDTO {

    private UUID uuid;
    private String name;

}
