package com.tsuyognote.tsuyogbasnet.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.tsuyognote.tsuyogbasnet.data.NoteItems;

/**
 * Created by tsuyogbasnet on 9/1/15.
 */
public class NoteEditor extends Activity {
    private NoteItems note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_editor);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        note = new NoteItems();
        note.setKey(intent.getStringExtra("key"));
        note.setValue(intent.getStringExtra("value"));

        EditText editText = (EditText) findViewById(R.id.noteText);
        editText.setText(note.getValue());
        editText.setSelection(note.getValue().length());
    }
    private void saveAndFinish(){
        EditText editText = (EditText) findViewById(R.id.noteText);
        String noteText = editText.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("key",note.getKey());
        intent.putExtra("value", noteText);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            saveAndFinish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}

