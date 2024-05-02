package com.example.shopsmart;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        mAuth = FirebaseAuth.getInstance();
        // Go back to LogIn Activity
        TextView signInOption = findViewById(R.id.textView42);
        signInOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        Button resetPass = findViewById(R.id.button2);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText resetEmail = findViewById(R.id.email);
                String currentEmail = resetEmail.getText().toString();
                boolean isValidEmail = currentEmail.length() > 5 && currentEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
                if (isValidEmail) {
                    mAuth.sendPasswordResetEmail(currentEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Reset email sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Drawable drawable = getDrawable(R.drawable.remove);
                    drawable.setBounds(0, 0, 50, 50);
                    resetEmail.setError("Invalid email", drawable);
                }
            }
        });
    }
}
