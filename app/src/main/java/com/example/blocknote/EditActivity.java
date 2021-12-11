package com.example.blocknote;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.blocknote.adapter.AdapterConstants;
import com.example.blocknote.adapter.MainAdapter;
import com.example.blocknote.adapter.Note;
import com.example.blocknote.db.DbManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private DbManager dbManager;
    private EditText editTitle, editDescription;
    private ImageButton changePictureButton, deletePictureButton;
    private String imageuri = "empty";
    private boolean isEditState = true;
    private FloatingActionButton addPicture;
    private ImageView newImage;
    private ConstraintLayout imageLayout;
    private final int PICK_IMAGE_CODE = 123;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getIntents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openWriteDb();
    }

    private void init() {
        dbManager = new DbManager(this);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        imageLayout = findViewById(R.id.imageLayout);
        addPicture = findViewById(R.id.addPictureButton);
        changePictureButton = findViewById(R.id.changePictureButton);
        deletePictureButton = findViewById(R.id.deletePictureButton);
        newImage = findViewById(R.id.newImage);
    }

    public void getIntents() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra(AdapterConstants.NOTE_INTENT);
            isEditState = intent.getBooleanExtra(AdapterConstants.EDIT_STATE, true);
            if (!isEditState) {
                editTitle.setText(note.getTitle());
                editDescription.setText(note.getDescription());
                if (!note.getUri().equals("empty")) {
                    imageLayout.setVisibility(View.VISIBLE);
                    newImage.setImageURI(Uri.parse(note.getUri()));
                    imageuri = note.getUri();
                }
            }
        }
    }


    //При нажатии на кнопку SAVE
    public void onClickSave(View view) {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.equals("") ||
                editDescription.getText().toString().equals("")) {
            Toast.makeText(this, R.string.AlertNoTitle, Toast.LENGTH_SHORT).show();
        } else {
            if (isEditState) {
                //записать в БД
                dbManager.insertDb(title, description, imageuri);
                Toast.makeText(this, R.string.AlertSaved, Toast.LENGTH_SHORT).show();

            } else {
                dbManager.updateValues(note.getId(), title, description, imageuri);
                Toast.makeText(this, R.string.AlertSaved, Toast.LENGTH_SHORT).show();
            }
            dbManager.closeDb();
            finish();
        }
    }

    //При нажатии на кнопку добавления картинки
    public void onClickAddImage(View view) {
        imageLayout.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        newImage.setImageResource(R.drawable.ic_baseline_default);

    }

    //При нажатии на кнопку изменения картинки
    public void onClickChangeImage(View view) {
        Intent chooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooser.setType("image/*");
        startActivityForResult(chooser, PICK_IMAGE_CODE);
    }

    //При нажатии на кнопку удаления картинки
    public void onClickDeleteImage(View view) {
        imageLayout.setVisibility(View.GONE);
        addPicture.setVisibility(View.VISIBLE);
        newImage.setImageResource(R.drawable.ic_baseline_default);
        imageuri = "empty";
    }

    //возвращение ссылки на картинку
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_CODE && data != null) {
            newImage.setImageURI(data.getData());
            imageuri = data.getData().toString();
            getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
}