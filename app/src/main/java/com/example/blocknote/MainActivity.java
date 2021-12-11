package com.example.blocknote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blocknote.adapter.MainAdapter;
import com.example.blocknote.db.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private MainAdapter mainAdapter;
    private RecyclerView rcView;
    private boolean isEditState = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.id_search);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dbManager.openReadDb();
                mainAdapter.updateAdapter(dbManager.readDbValues(newText));

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void init() {

        dbManager = new DbManager(this);
        rcView = findViewById(R.id.textList);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(mainAdapter);
        FloatingActionButton btn = findViewById(R.id.addButton);
        btn.setOnClickListener(this::onClickAdd);
    }


    //При открытии приложения
    @Override
    protected void onResume() {
        super.onResume();

        dbManager.openWriteDb();
        mainAdapter.updateAdapter(dbManager.readDbValues(""));
    }

    //При нажатии на кнопку "+" переход на другой экран
    public void onClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);

    }

    //При закрытии приложения
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }


    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.removeItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });
    }
}