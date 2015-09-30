package com.tsuyognote.tsuyogbasnet.notes;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsuyognote.tsuyogbasnet.*;
import com.tsuyognote.tsuyogbasnet.data.NoteItems;
import com.tsuyognote.tsuyogbasnet.data.NotesDataSource;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CODE = 101;
    private static final int MENU_DELETE_ID = 1002;
    private int currentNoteId;
    private NotesDataSource dataSource;
    List<NoteItems> notes;
    private static final String TAG ="NOTES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        registerForContextMenu(listView);

        //enabling touch to edit notes
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteItems noteItems = notes.get(position);
                Intent intent = new Intent(MainActivity.this, NoteEditor.class);
                intent.putExtra("key",noteItems.getKey());
                intent.putExtra("value", noteItems.getValue());
                startActivityForResult(intent, CODE);
            }
        });
        dataSource = new NotesDataSource(this);
        displayNotes();


    }

    private void displayNotes() {
        notes = dataSource.findAll();
        ArrayAdapter<NoteItems> adapter = new SimpleAdapter(MainActivity.this, R.layout.list_item_layout);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            createNote();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int)info.id;
        menu.add(0,MENU_DELETE_ID, 0, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_DELETE_ID){
            NoteItems noteItems = notes.get(currentNoteId);
            dataSource.remove(noteItems);
            displayNotes();
        }
        return super.onContextItemSelected(item);
    }


    private void createNote() {
        NoteItems noteItems = NoteItems.getNote();
        Intent intent = new Intent(this, NoteEditor.class);
        intent.putExtra("key",noteItems.getKey());
        intent.putExtra("value", noteItems.getValue());
        startActivityForResult(intent, CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CODE){
            NoteItems noteItems = new NoteItems();
            noteItems.setKey(data.getStringExtra("key"));
            noteItems.setValue(data.getStringExtra("value"));
            dataSource.updateNote(noteItems);
            displayNotes();
        }
    }

    private class SimpleAdapter extends ArrayAdapter<NoteItems>
    {
        public SimpleAdapter(Context context, int resource) {
            super(context, resource, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item_layout, parent, false);
            }
            NoteItems curentItem = notes.get(position);
            TextView noteText = (TextView)convertView.findViewById(R.id.noteItem);
            noteText.setText(curentItem.getValue());
            return convertView;
        }

    }
}
