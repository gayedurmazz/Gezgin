package com.example.gaye.gezgin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gaye.gezgin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etParola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        //kullanıcı giriş yapmışsa login arayüzünü hiç görmesin diye üzerine yazıldı.
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.et_email);
        etParola = (EditText) findViewById(R.id.et_parola);

        findViewById(R.id.btn_giris).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String password = etParola.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email ve parola boş bırakılamaz.", Toast.LENGTH_LONG).show();
                } else {
                    login(email, password);
                }
            }
        });

        findViewById(R.id.btn_kayit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


    }

    private void login(String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor lütfen bekleyiniz.");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
