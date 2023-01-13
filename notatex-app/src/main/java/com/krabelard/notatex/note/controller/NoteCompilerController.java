package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.service.ExternalLatexCompilerApiService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/compile")
@RequiredArgsConstructor
public class NoteCompilerController {

    private final ExternalLatexCompilerApiService compiler;

    @GetMapping(value = "/{noteUuid}")
    public ResponseEntity<byte[]> compileNote(@PathVariable UUID noteUuid) {
        val compiledNoteInfo = compiler.compileNote(noteUuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", compiledNoteInfo.getKey()))
                .body(compiledNoteInfo.getValue());
    }

}
