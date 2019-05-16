package com.example.vika.Activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vika.Classes.Constants;
import com.example.vika.Classes.Note;
import com.example.vika.R;

import java.util.Objects;

import io.paperdb.Paper;

public class NotesItemActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    EditText title, desc;
    TextView cancel, save;
    Toolbar toolbar;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_item);
        toolbar = findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        Paper.init(NotesItemActivity.this);
        initViews();
        setStyle();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotesItemActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("Status", 0);
                startActivity(i);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note newNote = new Note(title.getText().toString(), desc.getText().toString());
                if (Paper.book().contains("Counter")){
                    counter = Paper.book().read("Counter");
                    counter++;
                    Paper.book().write("Counter", counter);
                } else {
                    counter = 0;
                    Paper.book().write("Counter", counter);
                }
                String count = String.valueOf(counter);
                Paper.book().write(count, newNote);
                Intent i = new Intent(NotesItemActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("Status", 1);
                startActivity(i);
            }
        });
    }

    public void initViews(){
        appBarLayout = findViewById(R.id.app_bar);
        title = findViewById(R.id.note_title);
        desc = findViewById(R.id.note_desc);
        cancel = findViewById(R.id.cancel_note);
        save = findViewById(R.id.save_note);
    }

    public void setStyle(){
        if(!Objects.equals(Paper.book().read(Constants.THEME), null)
                && Objects.equals(Paper.book().read(Constants.THEME), "1")){
            appBarLayout.setBackgroundColor(getColor(R.color.colorGreenTheme));
        } else {
            appBarLayout.setBackgroundColor(getColor(R.color.colorPrimary));
        }
    }
}