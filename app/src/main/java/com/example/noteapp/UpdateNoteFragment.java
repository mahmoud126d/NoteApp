package com.example.noteapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.noteapp.db.Note;

public class UpdateNoteFragment extends Fragment {
    Context context;
    EditText noteText;
    Note note;
    Button updateButton;
    Button cancelButton;
    Communicator communicator;

    public UpdateNoteFragment() {
        // Required empty public constructor
    }

    public UpdateNoteFragment(Context context, Note note) {
        // Required empty public constructor
        this.context = context;
        this.note = note;
    }

    public static UpdateNoteFragment newInstance(String param1, String param2) {
        UpdateNoteFragment fragment = new UpdateNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        communicator = (Communicator) getActivity();
        noteText = view.findViewById(R.id.et_note_edit);
        updateButton = view.findViewById(R.id.btn_update);
        cancelButton = view.findViewById(R.id.btn_cancel_update);
        noteText.setText(note.getNoteTitle());
        updateButton.setOnClickListener(v -> {
            note.setNoteTitle(noteText.getText().toString());
            communicator.updateNote(note);
            closeFragment();
        });
        cancelButton.setOnClickListener(v -> {

            closeFragment();
        });
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this).commit();
    }
}