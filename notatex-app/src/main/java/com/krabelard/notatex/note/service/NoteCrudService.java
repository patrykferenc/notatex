package com.krabelard.notatex.note.service;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.mapper.NoteMapper;
import com.krabelard.notatex.note.domain.model.Note;
import com.krabelard.notatex.note.exception.exceptions.NoteConflictException;
import com.krabelard.notatex.note.exception.exceptions.NoteIOException;
import com.krabelard.notatex.note.exception.exceptions.NoteNotFoundException;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteCrudService {

    private final NoteRepository repository;

    private final NoteMapper mapper = NoteMapper.INSTANCE;

    public UUID create(MultipartFile noteFile) {
        val fileName = StringUtils.cleanPath(Objects.requireNonNull(noteFile.getOriginalFilename()));
        val checkNote = repository.findByName(fileName);
        if (checkNote.isPresent()) {
            throw new NoteConflictException(fileName);
        }

        byte[] content;
        try {
            content = noteFile.getBytes();
        } catch (IOException e) {
            throw new NoteIOException(noteFile.getOriginalFilename());
        }

        val note = new Note();
        note.setUuid(UUID.randomUUID());
        note.setName(fileName);
        note.setContents(content);

        return repository.save(note).getUuid();
    }

    public List<NoteDTO> fetchNoteList() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .toList();
    }

    public AbstractMap.SimpleEntry<String, byte[]> downloadNote(UUID noteUuid) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new NoteNotFoundException(noteUuid));

        return new AbstractMap.SimpleEntry<>(note.getName(), note.getContents());
    }

    public NoteDTO update(UUID noteUuid, MultipartFile noteFile) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new NoteNotFoundException(noteUuid));

        val fileName = StringUtils.cleanPath(Objects.requireNonNull(noteFile.getOriginalFilename()));
        note.setName(fileName);
        try {
            note.setContents(noteFile.getBytes());
        } catch (IOException e) {
            throw new NoteIOException(noteUuid.toString());
        }

        return mapper.entityToDto(repository.save(note));
    }

    public void delete(UUID noteUuid) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new NoteNotFoundException(noteUuid));

        repository.delete(note);
    }

}
