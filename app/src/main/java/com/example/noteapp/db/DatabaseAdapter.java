package com.example.noteapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseAdapter {
    private static DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public Long insertNoteTitle(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NOTE_TITLE, note.getNoteTitle());

        // db.insert returns the new row ID, not just success/failure
        long newId = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        note.setNoteId((int) newId);  // Set the ID back to the note object

        //db.close();
        return newId;
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getString(1));  // Gets title
            note.setNoteId(cursor.getInt(0));  // Gets ID from first column
            notes.add(note);
        }

        cursor.close();
        //db.close();
        return notes;
    }

    public int deleteNote(Note note) {
        int res = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] whereArgs = new String[]{String.valueOf(note.getNoteId())};

        int rowsAffected = db.delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.ID + " = ?",
                whereArgs);
        if (rowsAffected > 0) {
            res = 6;
        }
        //db.close();
        return res;
    }

    public int updateNote(Note note) {
        int res = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NOTE_TITLE, note.getNoteTitle());
        String[] whereArgs = new String[]{String.valueOf(note.getNoteId())};

        int rowsAffected = db.update(DatabaseHelper.TABLE_NAME,
                contentValues,
                DatabaseHelper.ID + " = ?",
                whereArgs);
        if (rowsAffected > 0) {
            res = 6;
        }
        //db.close();
        return res;
    }

}
