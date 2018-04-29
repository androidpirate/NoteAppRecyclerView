package com.example.android.noteapprecyclerview.adapter;

import com.example.android.noteapprecyclerview.R;
import com.example.android.noteapprecyclerview.model.Note;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter class to bind data to RecyclerView.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.bindNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        if(notes.size() == 0) {
            return 0;
        } else {
            return notes.size();
        }
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public NoteHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
        }

        public void bindNote(Note note) {
            if(note.getTitle() != null && note.getDescription() != null) {
                title.setText(note.getTitle());
                description.setText(note.getDescription());
            } else {
                throw new NullPointerException("Note fields are null.");
            }
        }
    }
}
