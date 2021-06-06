package com.example.stonks_asv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stonks_asv.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;

    //Variables de datos de registro
    private String name ="";
    private String email ="";
    private String password ="";

    FirebaseAuth mAuth;
    /*DatabaseReference mDatabase;*/
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        /*mDatabase = FirebaseDatabase.getInstance().getReference();*/

        etName = (EditText) findViewById(R.id.editTextName);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        iniciarFirebase();

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                Toast.makeText(RegisterActivity.this, reference.toString(), Toast.LENGTH_SHORT).show();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6) {
                        registerUser();
                    } else {
                        Toast.makeText(RegisterActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Debe introducir todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(siguiente);
            }
        });
    }
    private void iniciarFirebase() {

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void registerUser()
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
            if(task.isSuccessful())
            {
                String id = mAuth.getCurrentUser().getUid();
                /*Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("password", password);*/
                int scores = 0;
                User user = new User();
                user.setUid(id);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setScore1("0");

                /*Game game = new Game();
                game.setUid(UUID.randomUUID().toString());
                game.setName("Prueba");
                game.setDescription("Description");
                game.setGender("Gender");
                game.setDeveloper("Developer");
                //String id = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
                //String id = mAuth.getCurrentUser().getUid();
                Toast.makeText(MainActivity.this, reference.toString(), Toast.LENGTH_SHORT).show();
                reference.child("Games").child(game.getUid()).setValue(game);*/

                reference.child("Users").child(user.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful())
                        {
                            Intent siguiente = new Intent(RegisterActivity.this, BeginActivity.class);
                            startActivity(siguiente);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "No se pudo crear los datos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            else
            {
                //Toast.makeText(MainActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

            }
        });
    }
}