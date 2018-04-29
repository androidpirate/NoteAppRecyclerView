package com.example.android.noteapprecyclerview.utils;

import com.example.android.noteapprecyclerview.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a list of notes.
 */
public class FakeDataUtils {
    public static final String LOREM_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla " +
            "pellentesque cursus neque ut blandit. Suspendisse elit mi, fringilla vestibulum dictum blandit," +
            " vehicula at metus. Quisque pretium, enim non ultrices lobortis, metus mauris imperdiet nulla, " +
            "id tincidunt tortor enim ac lectus. Vestibulum eu egestas augue, ut malesuada nunc. " +
            "Aliquam blandit bibendum eros ac dapibus. ";

    public static List<Note> getFakeNotes() {
        List<Note> notes = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            notes.add(new Note("Title " + i, LOREM_TEXT));
        }
        return notes;
    }
}
