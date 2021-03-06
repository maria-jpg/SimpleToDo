package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // informs android dev that main activity has been created.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // sets the lay out.

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);


        loadItems();
        //items.add("Buy eggs");
        //items.add("Read a book!");
        //items.add("Have a good time!");

       ItemsAdapter.OnLongClickListener onLongClickListener =  new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed.", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
       ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
           @Override
           public void onItemClicked(int position) {
            Log.d("MainActivity", "Single click at position" + position);
            // create the new activity
               Intent i = new Intent(MainActivity.this, EditActivity.class);
            // pass the data being edited
                i.putExtra(KEY_ITEM_TEXT,items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
            // display activity
               startActivityForResult(i,EDIT_TEXT_CODE);


           }
       };
       itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etItem.getText().toString();
                // Add item to the model
                items.add(todoItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added.", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });
    }
    // handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // retrieve the updated text value (updated contents of the item)
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            // extract the original position of the edited item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            // update model with new item text
            items.set(position, itemText);

            // notify the adapter
            itemsAdapter.notifyItemChanged(position);
            // persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated.", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivity result");
        }
    }

    private File getDataFile(){

        return new File(getFilesDir(), "data.txt");
    }
    // this function will read load items by reading every line of the data file
    private void loadItems() {
        //items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }
    // this function saves items by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}