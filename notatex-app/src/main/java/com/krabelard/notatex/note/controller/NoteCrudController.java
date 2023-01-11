package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.mapper.NoteMapper;
import com.krabelard.notatex.note.domain.model.Note;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Controller for CRUD operations related to notes.
 *
 * @author jlaba
 */
@RestController
@RequestMapping("/api/notes")
public class NoteCrudController {

    @Autowired
    private NoteRepository repository;
    private final NoteMapper mapper = NoteMapper.INSTANCE;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadNote(@RequestParam("note") MultipartFile noteFile) throws NoSuchMethodException {
        val checkNote = repository.findByName(noteFile.getName());
        if (checkNote.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        val note = new Note();
        note.setUuid(UUID.randomUUID());
        note.setName(noteFile.getName());
        try {
            note.setContents(noteFile.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.created(
                linkTo(NoteCrudController.class
                        .getMethod("serveNote", UUID.class), note.getUuid())
                        .toUri()
        ).build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<String>> fetchNoteList() {
        return ResponseEntity.ok(repository.findAll()
                .stream()
                .map(Note::getName)
                .toList());
    }

    @GetMapping(
            value = "/{noteUuid}",
            produces = "text/plain"
    )
    public ResponseEntity<URL> serveNote(@PathVariable UUID noteUuid) throws IOException {
        val noteBytes = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContents();

        return ResponseEntity.ok(new ByteArrayResource(noteBytes).getURL());
    }

    @PutMapping("/{noteUuid}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable UUID noteUuid, @RequestParam("note") MultipartFile updatedNote) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        note.setName(updatedNote.getName());
        try {
            note.setContents(updatedNote.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(mapper.entityToDto(repository.save(note)));
    }

    @DeleteMapping("/{noteUuid}")
    public ResponseEntity<?> deleteNote(@PathVariable UUID noteUuid) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(note);

        return ResponseEntity.ok(null);
    }

}
