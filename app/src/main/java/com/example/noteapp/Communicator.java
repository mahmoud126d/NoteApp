package com.example.noteapp;

import com.example.noteapp.db.Note;

public interface Communicator {
    void deleteNote(Note note);
    void updateNote(Note note);
    void showEditFragment(Note note);
    void addNote(Note note);
}
