package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.db.DatabaseAdapter;
import com.example.noteapp.db.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Communicator{
    FloatingActionButton fab;
    AddNoteFragment addNoteFragment;
    UpdateNoteFragment updateNote;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    ArrayList<Note> notes;
    RecyclerView recyclerView;
    DatabaseAdapter databaseAdapter;
    Button saveButton;
    ConstraintLayout constraintLayout;
    Intent incomingIntent;
    public static final int ARABIC = 0;
    public static final int ENGLISH = 1;
    private static boolean isLanguageChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incomingIntent = getIntent();
        Log.d("lang", "onCreate: "+incomingIntent.getIntExtra("language",000));

        if(incomingIntent.getIntExtra("language",000)==ARABIC&&!isLanguageChanged){
            LocaleHelper.setLocale(MainActivity.this,"ar");
            recreate();
            isLanguageChanged=true;
            //this.getResources().set
        }else if(!isLanguageChanged){
            LocaleHelper.setLocale(MainActivity.this,"en");
            recreate();
            isLanguageChanged=true;
        }

        constraintLayout = findViewById(R.id.main);
        notes = new ArrayList<>();
        databaseAdapter = new DatabaseAdapter(this);
        notes = databaseAdapter.getNotes();
        recyclerView = findViewById(R.id.rv_notes);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,notes);
        recyclerView.setAdapter(adapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showAddNoteFragment();
            recyclerView.setClickable(false);
        });
    }

    private void showAddNoteFragment(){
        addNoteFragment = new AddNoteFragment(this);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.add_note_fragment,addNoteFragment,"add-fragment");
        transaction.commit();
        recyclerView.setAlpha(0f);
    }
    public void addNote(Note note){
        recyclerView.setAlpha(1);
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        if (databaseAdapter.insertNoteTitle(new Note(note.getNoteTitle())) > 0) {
            notes = databaseAdapter.getNotes();
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,notes);
            recyclerView.setAdapter(adapter);
            Log.d("dbResult", "added to db");
        } else {
            Log.d("dbResult", "not added to db");
        }
    }
    private void updateNotes(){
        notes = databaseAdapter.getNotes();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,notes);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void deleteNote(Note note) {
        if(databaseAdapter.deleteNote(note)>0){
            Toast.makeText(this,"Note Deleted",Toast.LENGTH_SHORT).show();
            updateNotes();
        }else{
            Log.d("db", "note not deleted");
        }
    }
    @Override
    public void updateNote(Note note) {
        recyclerView.setAlpha(1);
        Log.d("TAG", "updateNote: ");
        if(databaseAdapter.updateNote(note)>0){
            Log.d("db", "note updated");
            updateNotes();
        }else{
            Log.d("db", "note not updated");
        }
    }
    @Override
    public void showEditFragment(Note note) {
        recyclerView.setAlpha(0f);
        updateNote = new UpdateNoteFragment(this,note);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.update_Note_Fragment_Container,updateNote,"add-fragment");
        transaction.commit();
    }
}