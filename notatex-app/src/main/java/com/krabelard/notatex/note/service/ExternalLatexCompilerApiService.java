package com.krabelard.notatex.note.service;

import com.krabelard.notatex.note.exception.exceptions.NoteIOException;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.UUID;

@Service
public class ExternalLatexCompilerApiService {

    @Autowired
    private NoteRepository repository;

    public AbstractMap.SimpleEntry<String, byte[]> compileNote(UUID noteUuid) {
        val note = repository.findByUuid(noteUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        val noteBytes = new ByteArrayResource(note.getContents());
        val compilerURL = "http://latex-compiler:8000/compile-tex";
        val requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.asMediaType(MimeType.valueOf("application/x-tex")));
        val restTemplate = new RestTemplate();
        val requestEntity = new HttpEntity<>(noteBytes, requestHeaders);
        val response = restTemplate.exchange(compilerURL, HttpMethod.POST, requestEntity, Resource.class);

        try {
            val compiledBytes = Objects.requireNonNull(response.getBody()).getInputStream().readAllBytes();
            return new AbstractMap.SimpleEntry<>(note.getName(), compiledBytes);
        } catch (IOException e) {
            throw new NoteIOException(noteUuid.toString());
        }
    }

}
