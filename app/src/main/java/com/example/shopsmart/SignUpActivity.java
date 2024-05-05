package com.example.shopsmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String TAG = "SUCCESS";
    String FAIL = "FAILURE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        setupSignupContent();
        Button signUpButton = findViewById(R.id.button3);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
        TextView signInOption = findViewById(R.id.textView17);
        // If the user has registered
        signInOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
    void handleSignUp() {
        TextInputEditText username = findViewById(R.id.email);
        TextInputEditText email = findViewById(R.id.editTextText3);
        TextInputEditText password = findViewById(R.id.editTextText5);
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();
        String inputUserName = username.getText().toString();
        Drawable drawable = getDrawable(R.drawable.remove);
        drawable.setBounds(0, 0, 50, 50);
        boolean isValidEmail = inputEmail.length() > 0 && inputEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        boolean isValidPassWord = inputUserName.length() > 5;
        boolean isValidUserName = inputUserName.length() > 5;
        boolean hasError = false;
        if (!isValidEmail) {
            email.setError("Email is invalid", drawable);
            hasError = true;
        }
        if (!isValidUserName) {
            username.setError("Username must have at least 6 characters", drawable);
            hasError = true;
        }
        if (!isValidPassWord) {
            password.setError("Password must have at least 6 characters", drawable);
            hasError = true;
        }
        if (hasError) {
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(inputUserName).build();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            }
                        }
                    });
                    Intent intent = new Intent(SignUpActivity.this, ScreenActivity1.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
                else {
                    Log.w(FAIL, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void setupSignupContent() {
        TextView text = findViewById(R.id.textView3);
        text.setText(getResources().getString(R.string.text_text));

        TextPaint paint = text.getPaint();
        float width = paint.measureText(getResources().getString(R.string.text_text));
        Shader textShader = new LinearGradient(0, 0, width, text.getTextSize(),
                new int[]{
                        Color.parseColor("#9747FF"),
                        Color.parseColor("#8BA9DC"),
                }, null, Shader.TileMode.CLAMP);
        text.getPaint().setShader(textShader);
    }

}
