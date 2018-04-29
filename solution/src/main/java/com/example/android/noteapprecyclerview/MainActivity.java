package com.example.android.noteapprecyclerview;

import com.example.android.noteapprecyclerview.adapter.NoteAdapter;
import com.example.android.noteapprecyclerview.model.Note;
import com.example.android.noteapprecyclerview.utils.FakeDataUtils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Note> notes;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get fake data
        notes = FakeDataUtils.getFakeNotes();
        recyclerView = findViewById(R.id.rv_note_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set adapter
        NoteAdapter adapter = new NoteAdapter(notes);
        recyclerView.setAdapter(adapter);
    }
}
