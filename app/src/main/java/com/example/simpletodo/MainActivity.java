package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnadd;
    EditText etItem;
    RecyclerView rvItems;

    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);





        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener
                = new ItemsAdapter.OnLongClickListener()
        {

            @Override
            public void onItemLongClicked(int position)
            {
                //remove from list of strings
                items.remove(position);

                itemsAdapter.notifyItemRemoved(position);

                Toast.makeText(MainActivity.this,"item removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemsAdapter =  new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        items.add("Buy Milk");
        items.add("Go to the Gym");


        btnadd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               String todoItem =  etItem.getText().toString();
               //add to the model
                items.add(todoItem);
                //Notify we have inserted an item
                itemsAdapter.notifyItemInserted(items.size() - 1);

                etItem.setText("");
                Toast.makeText(MainActivity.this,"item added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }


    private File getDataFile()
    {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems()
    {
        try
        {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));

        }
        catch (IOException e) {
            Log.e("Main","Error reading items");
            e.printStackTrace();
        }
    }

    private void saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main","Error writing items");
            e.printStackTrace();
        }
    }


}