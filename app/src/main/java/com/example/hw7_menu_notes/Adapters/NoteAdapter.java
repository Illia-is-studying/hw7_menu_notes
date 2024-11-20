package com.example.hw7_menu_notes.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw7_menu_notes.Models.Note;
import com.example.hw7_menu_notes.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_note_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        long id = note.getNoteId();
        String publicationDate = note.getPublicationDate();
        String content = note.getContent();
        if(content.length() > 25) {
            content = content.substring(0, 25) + "...";
        }

        holder.publicationDateTextView.setText(publicationDate);
        holder.shortTextTextView.setText(content);
        holder.shortTextTextView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView shortTextTextView;
        public TextView publicationDateTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            shortTextTextView = itemView.findViewById(R.id.note_short_text);
            publicationDateTextView = itemView.findViewById(R.id.note_publication_date_time);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            menu.add(this.getAdapterPosition(), 1, 0, "Edit");
            menu.add(this.getAdapterPosition(), 2, 1, "Delete");
        }
    }
}
