package com.krabelard.notatex.note.repository;

import com.krabelard.notatex.note.domain.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByUuid(UUID uuid);

    Optional<Note> findByName(String name);

}
