package com.example.noteapp.db;
public class Note {
    private int noteId;
    private String noteTitle;

    public Note(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    // Getters and setters
    public int getNoteId() { return noteId; }
    public void setNoteId(int id) { this.noteId = id; }
    public String getNoteTitle() { return noteTitle; }
    public void setNoteTitle(String title) { this.noteTitle = title; }
}