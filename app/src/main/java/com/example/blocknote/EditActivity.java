package com.example.blocknote;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blocknote.adapter.MainAdapter;
import com.example.blocknote.db.DbManager;

public class EditActivity extends AppCompatActivity {
    private DbManager dbManager;
    private MainAdapter mainAdapter;
    private EditText editTitle, editDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        dbManager.openWriteDb();

    }

    private void init() {
        dbManager = new DbManager(this);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        mainAdapter = new MainAdapter(this);
    }

    //При нажатии на кнопку SAVE
    public void onClickSave(View view) {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.equals("") ||
                editDescription.getText().toString().equals("")) {
            Toast.makeText(this, R.string.AlertNoTitle, Toast.LENGTH_SHORT).show();
        } else {
            //записать в БД
            dbManager.insertDb(title, description);
            Toast.makeText(this, R.string.AlertSaved, Toast.LENGTH_SHORT).show();
            dbManager.closeDb();
            finish();
        }
    }
}