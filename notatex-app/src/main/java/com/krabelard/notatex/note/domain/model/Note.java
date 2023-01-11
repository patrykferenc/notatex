package com.krabelard.notatex.note.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "name", nullable = false, unique = true, length = 31)
    private String name;

    @Lob
    @Column(name = "contents")
    private byte[] contents;

}
