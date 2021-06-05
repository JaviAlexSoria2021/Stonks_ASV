package com.example.stonks_asv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stonks_asv.models.Game;
import com.example.stonks_asv.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnGoToRegister;

    //Variables de datos de inicio de sesion
    private String email ="";
    private String password ="";

    private FirebaseAuth mAuth;
    /*DatabaseReference mDatabase;*/
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mDatabase = FirebaseDatabase.getInstance().getReference();*/

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGoToRegister = (Button) findViewById(R.id.btnGoToRegister);


        iniciarFirebase();

        mAuth = FirebaseAuth.getInstance();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    loginUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Introduzca todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                //finish();
            }
        });
    }
    private void iniciarFirebase() {

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this, BeginActivity.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Datos introducidos incorrectos", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(MainActivity.this, BeginActivity.class));
            finish();
        }
    }
}