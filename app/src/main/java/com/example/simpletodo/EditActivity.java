package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText etItem_;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem_ = findViewById(R.id.etItem_);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit Item");


       etItem_.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
       // when the user is done editing, they click on the save button
       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // create an intent which will contain the results
               Intent intent = new Intent(); // using as a shell to pass data
               // pass the data (results of the string)
               intent.putExtra(MainActivity.KEY_ITEM_TEXT, etItem_.getText().toString());
               intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
               // set the result of the intent
               setResult(RESULT_OK, intent);
               // finish the activity, close the screen and go back
               finish();
           }
       });
    }
}