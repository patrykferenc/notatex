package com.krabelard.notatex.note.controller;

import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@RequestMapping("/api/compile")
public class NoteCompilerController {

    @Autowired
    private NoteRepository repository;

    /**
     * Compiles note with given id to pdf
     *
     * @param noteId id of note to be compiled
     * @throws ResponseStatusException <p> 404 not found - note with given id not found
     *                                 <p> 500 internal server error - java shit itself while reading the file and threw {@link IOException}
     */
    @GetMapping(value = "/{noteId}")
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
