package com.krabelard.notatex.note.domain.mapper;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note dtoToEntity(NoteDTO dto);

    NoteDTO entityToDto(Note entity);

}
