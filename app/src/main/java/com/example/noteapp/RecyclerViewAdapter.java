package com.example.noteapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.db.Note;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Note> notes;
    private final Communicator communicator;
    public RecyclerViewAdapter(Context context, ArrayList<Note> notes){
        this.context = context;
        this.notes=notes;
        communicator =(Communicator) context;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_note_information,parent,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.noteTitle.setText(notes.get(position).getNoteTitle());
        holder.editButton.setOnClickListener(v->{
            Log.d("btn", "edit button pressed");
            communicator.showEditFragment(notes.get(position));
            //communicator.updateNote(notes.get(position));
        });
        holder.deleteButton.setOnClickListener(v->{
            Log.d("btn", "delete button pressed");
            communicator.deleteNote(notes.get(position));

        });
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        ConstraintLayout constraintLayout;
        View layout;
        Button editButton;
        Button deleteButton;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            noteTitle = itemView.findViewById(R.id.tv_note_title);
            constraintLayout = itemView.findViewById(R.id.add_note_fragment);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
