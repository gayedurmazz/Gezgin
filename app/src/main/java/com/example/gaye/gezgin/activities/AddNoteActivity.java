package com.example.gaye.gezgin.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.gaye.gezgin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    EditText etUserNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etUserNote = (EditText) findViewById(R.id.et_usernote);

        findViewById(R.id.btn_new_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        findViewById(R.id.btn_notlara_don).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addNote() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("GezdiğimYerler");
        String notesID = myRef.push().getKey();
        String receivedUserNote = etUserNote.getText().toString();
        if (receivedUserNote.length() > 0) {
            myRef.child(notesID).child("sehirAdi").setValue(receivedUserNote);
            showDialog("Başarılı", "Notunuz Kaydedildi!");
        } else {
            showDialog("İşlem Başarısız", "Not alanı boş bırakılamaz!");
        }
        //Yeni kayıt oluşturabilmek için
        etUserNote.setText("");
    }

    private void showDialog(String title, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(AddNoteActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("TAMAM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
