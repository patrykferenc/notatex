package com.krabelard.notatex.note.service;

import com.krabelard.notatex.note.exception.exceptions.NoteIOException;
import com.krabelard.notatex.note.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExternalLatexCompilerApiService {

    private final NoteRepository noteRepository;

    public AbstractMap.SimpleEntry<String, byte[]> compileNote(UUID noteUuid) {
        val note = noteRepository.findByUuid(noteUuid)
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
