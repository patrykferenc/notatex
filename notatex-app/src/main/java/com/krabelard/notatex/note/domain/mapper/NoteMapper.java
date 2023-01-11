package com.krabelard.notatex.note.domain.mapper;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "contents", ignore = true)
    Note dtoToEntity(NoteDTO dto);

    NoteDTO entityToDto(Note entity);

}
