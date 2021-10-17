package com.example.blocknote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blocknote.adapter.MainAdapter;
import com.example.blocknote.db.DbManager;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private RecyclerView rcView;
    private MainAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {

        dbManager = new DbManager(this);
        rcView = findViewById(R.id.textList);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        rcView.setAdapter(mainAdapter);
    }

    //При открытии приложения
    @Override
    protected void onResume() {
        super.onResume();

        dbManager.openWriteDb();
        mainAdapter.updateAdapter(dbManager.readDbValues());
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
}