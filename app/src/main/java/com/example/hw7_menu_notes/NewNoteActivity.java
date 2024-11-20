package com.example.hw7_menu_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw7_menu_notes.Models.Note;

public class NewNoteActivity extends AppCompatActivity {
    private static String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);

        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        long id = intent.getLongExtra("id", 0);

        TextView textView = findViewById(R.id.new_note_text_view);
        EditText editText = findViewById(R.id.new_note_edit_text);
        Button submitButton = findViewById(R.id.new_note_submit_button);

        if(id != 0) {
            editText.setText(content);
            editText.setTag(id);

            textView.setText("Your Note");
            submitButton.setText("Edit");
            action = "edit";
        } else {
            textView.setText("New Note");
            submitButton.setText("Save");
            action = "add";
        }
    }

    public void addNewNote(View view) {
        EditText editText = findViewById(R.id.new_note_edit_text);
        String content = editText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("content", content);
        intent.putExtra("action", action);

        if(action.equals("edit")) {
            intent.putExtra("id", (Long) editText.getTag());
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
