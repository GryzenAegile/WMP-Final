package com.example.enrollment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private Button loginButton, signUpButton;
    private EditText txtEmail, txtPassword, txtConfirmPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views and Buttons
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();

        // Sign Up Function Logic
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmpassword = txtConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    txtEmail.setError("Please enter an email address.");
                    return;
                } else if (TextUtils.isEmpty(password)){
                    txtPassword.setError("Please enter a password.");
                    return;
                } else if (TextUtils.isEmpty(confirmpassword)){
                    txtPassword.setError("Please re-enter a password.");
                    return;
                } else {
                    signUpUser(email, password, confirmpassword);
                }
            }
        });

        // Login Function Logic
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpUser(String email, String password, String confirmpassword){
        // Validate email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please enter a valid email address.");
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            txtPassword.setError("Password must be at least 6 characters.");
            return;
        }

        // Validate password length
        if (!password.equals(confirmpassword)) {
            txtConfirmPassword.setError("Password must be the same.");
            return;
        }

        // Proceed with Firebase sign-up
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}