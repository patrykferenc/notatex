package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.domain.model.Note;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for CRUD operations related to notes.
 * @author jlaba
 */
@RestController
public class NoteCrudController {

    @Autowired
    NoteRepository repository;

    /**
     * Adds a new note to the database
     * @param noteFile file to be uploaded
     * @return id of created note
     * @throws ResponseStatusException:
     * <p> 409 conflict - trying to create note with name which already exists
     * <p> 500 internal server error - java shit itself while reading the file and threw {@link IOException}
     */
    @PostMapping("/api/upload")
    public long uploadNote(@RequestParam("note") MultipartFile noteFile) {
        val checkNote = repository.findByName(noteFile.getName());
        if (checkNote.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        val note = new Note();
        note.setName(noteFile.getName());
        try {
            note.setContents(noteFile.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return repository.save(note).getId();
    }

    /**
     * Gets set of all notes
     * @return {@link Set} of names of all notes (can be empty)
     */
    @GetMapping("/api/get")
    public Set<String> fetchNoteList() {
        return repository.findAll()
                .stream()
                .map(Note::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Downloads a note if it exists
     * @param noteId id of the note to be downloaded
     * @return {@link ByteArrayResource} with file
     * @throws ResponseStatusException
     * <p> 404 not found - note with given id not found
     */
    @GetMapping(value = "/api/get/{noteId}", produces = "text/plain")
    public Resource serveNote(@PathVariable long noteId) {
        val noteBytes = repository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContents();

        return new ByteArrayResource(noteBytes);
    }

    /**
     * Updates an exising note
     * @param noteId id of note to be updated
     * @throws ResponseStatusException
     * <p> 404 not found - note with given id not found
     * <p> 500 internal server error - java shit itself while reading the file and threw {@link IOException}
     */
    @PostMapping("/api/update/{noteId}")
    public void updateNote(@PathVariable long noteId, @RequestParam("note") MultipartFile updatedNote) {
        val note = repository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        note.setName(updatedNote.getName());
        try {
            note.setContents(updatedNote.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        repository.save(note);
    }

    /**
     * Deletes an existing note
     * @param noteId id of note to be deleted
     * @throws ResponseStatusException
     * <p> 404 not found - trying to delete non-existent note
     */
    @DeleteMapping("/api/delete/{noteId}")
    public void deleteNote(@PathVariable long noteId) {
        val note = repository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(note);
    }

    /**
     * Compiles note with given id to pdf
     * @param noteId id of note to be compiled
     * @throws ResponseStatusException
     * <p> 404 not found - note with given id not found
     * <p> 500 internal server error - java shit itself while reading the file and threw {@link IOException}
     */
    @PostMapping(value = "/api/compile/{noteId}")
    public Resource compileNote(@PathVariable long noteId) {
        val noteBytes = new ByteArrayResource(
            repository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContents()
        );
        val compilerURL = "http://latex-compiler:8000/compile-tex";
        val requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.asMediaType(MimeType.valueOf("application/x-tex")));
        val restTemplate = new RestTemplate();
        val requestEntity = new HttpEntity<>(noteBytes, requestHeaders);
        val response = restTemplate.exchange(compilerURL, HttpMethod.POST, requestEntity, Resource.class);
        return response.getBody();
    }

}
