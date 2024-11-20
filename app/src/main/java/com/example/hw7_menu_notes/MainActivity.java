package com.example.hw7_menu_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw7_menu_notes.Adapters.NoteAdapter;
import com.example.hw7_menu_notes.Models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private static List<Note> noteList;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data == null) {
                                return;
                            }

                            String action = data.getStringExtra("action");
                            if (action == null) {
                                return;
                            }
                            String content = data.getStringExtra("content");

                            if (action.equals("edit")) {
                                Long id = (Long) data.getSerializableExtra("id");

                                if (id != null && id != 0) {
                                    Note note = noteList.stream()
                                            .filter(n -> n.getNoteId() == id)
                                            .collect(Collectors.toList())
                                            .get(0);

                                    int noteIndex = noteList.indexOf(note);
                                    noteList.remove(noteIndex);
                                    noteList.add(noteIndex, new Note(content));

                                    noteAdapter.notifyItemChanged(noteIndex);
                                }
                            } else if (action.equals("add")) {
                                noteList.add(new Note(content));
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteList = new ArrayList<>();
        noteList.add(new Note("Test"));
        noteList.add(new Note("Test1"));
        noteList.add(new Note("Test2"));
        noteList.add(new Note("Test3"));

        noteAdapter = new NoteAdapter(noteList);

        recyclerView = findViewById(R.id.notes_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_settings) {
            Toast.makeText(this, "Hello, this is settings!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.item_add_note) {
            Intent intent = new Intent(this, NewNoteActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }

        return false;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            item.getItemId();
            int position = item.getGroupId();
            Note note = noteList.get(position);

            Intent intent = new Intent(this, NewNoteActivity.class);
            intent.putExtra("id", note.getNoteId());
            intent.putExtra("content", note.getContent());
            activityResultLauncher.launch(intent);
            return true;
        } else if (item.getItemId() == 2) {
            int pos = item.getGroupId();
            noteList.remove(pos);
            noteAdapter.notifyItemRemoved(pos);
            return true;
        }

        return false;
    }
}