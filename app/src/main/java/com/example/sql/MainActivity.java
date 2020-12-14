package com.example.sql;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView rvRecords;
    private HelperDB helperDB;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);
        rvRecords = findViewById(R.id.rvRecords);

        helperDB = new HelperDB(this);

        loadRecords();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Todos los registros");

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUpdate.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
            }
        });
    }

    private void loadRecords() {
        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this, helperDB.getAllRecords(
                Constants.C_ID + " ASC"
        ));
        rvRecords.setAdapter(adapterRecord);
        // actionBar.setSubtitle("Total: " + helperDB.countRecords());
    }

    private void searchRecords(String query) {
        AdapterRecord adapterRecord = new AdapterRecord(MainActivity.this, helperDB.searchRecords(query));
        rvRecords.setAdapter(adapterRecord);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}