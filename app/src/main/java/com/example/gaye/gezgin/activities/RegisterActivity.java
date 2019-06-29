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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_parola);
        etPassword2 = (EditText) findViewById(R.id.et_parola_onay);

        findViewById(R.id.btn_kayit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etPassword2.getText().toString();

                if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email ve parola alanları boş bırakılamaz", Toast.LENGTH_LONG).show();
                } else if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Parolalar eşleşmiyor", Toast.LENGTH_LONG).show();
                } else {
                    register(email, password);
                }
            }
        });
    }

    private void register(String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("İşleminiz yapılıyor lütfen bekleyniz.");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Kayıt Tamamlandı!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
