package com.krabelard.notatex.note.service;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.mapper.NoteMapper;
import com.krabelard.notatex.note.domain.model.Note;
import com.krabelard.notatex.note.exception.exceptions.NoteIOException;
import com.krabelard.notatex.note.exception.exceptions.NoteNotFoundException;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class NoteCrudService {

    @Autowired
    private NoteRepository repository;
    private final NoteMapper mapper = NoteMapper.INSTANCE;

    public UUID create(MultipartFile noteFile) {
        val checkNote = repository.findByName(noteFile.getName());
        if (checkNote.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        byte[] content;
        try {
            content = noteFile.getBytes();
        } catch (IOException e) {
            throw new NoteIOException(noteFile.getOriginalFilename());
        }

        val note = new Note();
        note.setUuid(UUID.randomUUID());
        note.setName(noteFile.getOriginalFilename());
        note.setContents(content);

        return repository.save(note).getUuid();
    }

    public List<String> fetchNameList() {
        return repository.findAll()
                .stream()
                .map(Note::getName)
                .toList();
    }

    public URL fetchNoteDownloadURL(UUID noteUuid) {
        val noteBytes = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContents();

        try {
            return new ByteArrayResource(noteBytes).getURL();
        } catch (IOException e) {
            throw new NoteIOException(noteUuid.toString());
        }
    }

    public NoteDTO update(UUID noteUuid, MultipartFile noteFile) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        note.setName(noteFile.getName());
        try {
            note.setContents(noteFile.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return mapper.entityToDto(repository.save(note));
    }

    public void delete(UUID noteUuid) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new NoteNotFoundException(noteUuid));

        repository.delete(note);
    }

}
