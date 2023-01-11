package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.domain.mapper.NoteMapper;
import com.krabelard.notatex.note.repository.NoteRepository;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private NoteCrudService crudService;
    private final NoteMapper mapper = NoteMapper.INSTANCE;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadNote(@RequestParam("note") MultipartFile noteFile) throws NoSuchMethodException {
        val uuid = crudService.create(noteFile);

        return ResponseEntity.created(
                linkTo(NoteCrudController.class.getMethod("serveNote", UUID.class), uuid).toUri()
        ).build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<String>> fetchNoteList() {
        return ResponseEntity.ok(crudService.fetchNameList());
    }

    @GetMapping(
            value = "/{noteUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<URL> serveNote(@PathVariable UUID noteUuid) {
        return ResponseEntity.ok(crudService.fetchNoteDownloadURL(noteUuid));
    }

    @PutMapping("/{noteUuid}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable UUID noteUuid, @RequestParam("note") MultipartFile noteFile) {
        return ResponseEntity.ok(crudService.update(noteUuid, noteFile));
    }

    @DeleteMapping("/{noteUuid}")
    public ResponseEntity<?> deleteNote(@PathVariable UUID noteUuid) {
        crudService.delete(noteUuid);

        return ResponseEntity.ok(null);
    }

}
