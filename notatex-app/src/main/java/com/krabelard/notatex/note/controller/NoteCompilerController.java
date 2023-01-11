package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.adapters.latex.ExternalLatexCompilerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.UUID;

@RestController
@RequestMapping("/api/compile")
public class NoteCompilerController {

    @Autowired
    private ExternalLatexCompilerApiService compiler;

    @GetMapping(value = "/{noteUuid}")
    public ResponseEntity<URL> compileNote(@PathVariable UUID noteUuid) {
        return ResponseEntity.ok(compiler.compileNote(noteUuid));
    }

}
