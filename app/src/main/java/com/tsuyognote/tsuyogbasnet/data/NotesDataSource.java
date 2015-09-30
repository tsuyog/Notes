package com.tsuyognote.tsuyogbasnet.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by tsuyogbasnet on 9/1/15.
 */
public class NotesDataSource {
    private static final String PREFKEY="notes";
    private SharedPreferences notePrefs;

    public NotesDataSource(Context context){
        notePrefs = context.getSharedPreferences(PREFKEY,context.MODE_PRIVATE);
    }
    public List<NoteItems> findAll (){
        Map<String, ?> notesMap = notePrefs.getAll();
        SortedSet<String> keys = new TreeSet<>(notesMap.keySet());
        List<NoteItems> noteItemsList = new ArrayList<>();
        for (String key : keys){
            NoteItems noteItems = new NoteItems();
            noteItems.setKey(key);
            noteItems.setValue((String) notesMap.get(key));
            noteItemsList.add(noteItems);
        }
//        List<NoteItems> noteItemsList = new ArrayList<NoteItems>();
//        NoteItems noteItems =  NoteItems.getNote();
//        noteItemsList.add(noteItems);

        return noteItemsList;
    }
    public boolean updateNote(NoteItems noteItems){
        SharedPreferences.Editor editor = notePrefs.edit();
        editor.putString(noteItems.getKey(), noteItems.getValue());
        editor.commit();
        return true;
    }
    public boolean remove(NoteItems noteItems){
        if (notePrefs.contains(noteItems.getKey())){
            SharedPreferences.Editor editor = notePrefs.edit();
            editor.remove(noteItems.getKey());
            editor.commit();
        }
        return true;
    }
}
