package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.domain.dto.NoteDTO;
import com.krabelard.notatex.note.service.NoteCrudService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@CrossOrigin("http://notatex-front")
@RequiredArgsConstructor
public class NoteCrudController {

    private final NoteCrudService crudService;

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
    public ResponseEntity<List<NoteDTO>> fetchNoteList() {
        return ResponseEntity.ok(crudService.fetchNoteList());
    }

    @GetMapping(
            value = "/{noteUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<byte[]> serveNote(@PathVariable UUID noteUuid) {
        val noteInfo = crudService.downloadNote(noteUuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", noteInfo.getKey()))
                .body(noteInfo.getValue());
    }

    @PutMapping(
            value = "/{noteUuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<NoteDTO> updateNote(@PathVariable UUID noteUuid, @RequestParam("note") MultipartFile noteFile) {
        return ResponseEntity.ok(crudService.update(noteUuid, noteFile));
    }

    @DeleteMapping("/{noteUuid}")
    public ResponseEntity<?> deleteNote(@PathVariable UUID noteUuid) {
        crudService.delete(noteUuid);

        return ResponseEntity.ok(null);
    }

}
