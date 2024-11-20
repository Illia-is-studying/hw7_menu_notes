package com.example.hw7_menu_notes.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note implements Serializable {
    private final static DateTimeFormatter formatter;
    private static long currentId;

    private long noteId;
    private String content;
    private LocalDateTime publicationDate;

    static {
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        currentId = 0L;
    }

    public Note(String content) {
        this.content = content;
        this.publicationDate = LocalDateTime.now();

        currentId++;
        noteId = currentId;
    }

    public Note(String content, LocalDateTime publicationDate) {
        this.content = content;
        this.publicationDate = publicationDate;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublicationDate() {
        return publicationDate.format(formatter);
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public long getNoteId() {
        return noteId;
    }
}
