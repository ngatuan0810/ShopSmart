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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String TAG = "SUCCESS";
    String FAIL = "FAILED";
    boolean noWarning = true;
    LoginButton loginButton;
    CallbackManager callBackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient client;
    final int FACEBOOK = 0;
    final int GOOGLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in); // Assuming the layout file for LogInActivity is activity_login.xml
        firebaseAuth = FirebaseAuth.getInstance();
        // Call the method to set up login content
        setupLoginContent();
        handleAppLogin();
        handleFacebookLogin();
        handleGoogleLogin();
    }

    // Method to set up login content
    void setupLoginContent() {
        TextView text = findViewById(R.id.textView);
        text.setText(getResources().getString(R.string.text_t));

        TextPaint paint = text.getPaint();
        float width = paint.measureText(getResources().getString(R.string.text_t));
        Shader textShader = new LinearGradient(0, 0, width, text.getTextSize(),
                new int[]{
                        Color.parseColor("#9747FF"),
                        Color.parseColor("#8BA9DC"),
                }, null, Shader.TileMode.CLAMP);
        text.getPaint().setShader(textShader);
        TextView signUpOption = findViewById(R.id.textView10);
        // If the user has not registered
        signUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        TextView resetPass = findViewById(R.id.textView4);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    void handleGoogleLogin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id)).requestEmail().build();
        client = GoogleSignIn.getClient(this, gso);
        TextView loginButton = findViewById(R.id.textView6);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleAccessToken(account.getIdToken(), GOOGLE);
            }
            catch(Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            callBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    void handleFacebookLogin() {
        callBackManager = CallbackManager.Factory.create();
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login");
                handleAccessToken(loginResult.getAccessToken().getToken(), FACEBOOK);
            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "Facebook:Cancel");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d("Error", "Facebook:Error", e);
            }
        });
        TextView fbLoginButton = findViewById(R.id.textView7);
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logInWithReadPermissions(LogInActivity.this, Arrays.asList("public_profile", "email"));
            }
        });
    }
    void handleAccessToken(String token, int provider) {
        Log.d(TAG, "handleAccessToken:" + token);
        AuthCredential credential;
        if (provider == FACEBOOK) {
            credential = FacebookAuthProvider.getCredential(token);
        }
        else {
            credential = GoogleAuthProvider.getCredential(token, null);
        }
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:Success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent intent = new Intent(LogInActivity.this, ScreenActivity2.class);
                    startActivity(intent);
                }
                else {
                    Log.w(FAIL, "signInWithCredential:Failure", task.getException());
                    Toast.makeText(LogInActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean handleFormValidation(String textValue) {
        if (textValue.length() < 6) {
            return false;
        }
        return true;
    }
    void authenticateUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent intent = new Intent(LogInActivity.this, ScreenActivity2.class);
                    startActivity(intent);
                }
                else {
                    Log.w(FAIL, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LogInActivity.this, "Email or password is incorrect", Toast.LENGTH_LONG);
                    LinearLayout parent = findViewById(R.id.parent);
                    if (noWarning) {
                        TextView error = new TextView(LogInActivity.this);
                        error.setText("Email or password is incorrect");
                        error.setPadding(31, 0, 0, 20);
                        error.setTextSize(18);
                        error.setTextColor(Color.parseColor("#F4491F"));
                        parent.addView(error, 2);
                        noWarning = false;
                    }
                }
            }
        });
    }
    void handleAppLogin() {
        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText password = findViewById(R.id.editTextText5);
        Button signInButton = findViewById(R.id.button2);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                boolean isValidEmail = handleFormValidation(inputEmail) && inputEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
                boolean isValidPassWord = handleFormValidation(password.getText().toString());
                Drawable drawable = getDrawable(R.drawable.remove);
                drawable.setBounds(0, 0, 50, 50);
                boolean hasError = false;
                if (!isValidEmail) {
                    email.setError("Email invalid", drawable);
                    hasError = true;
                }
                if (!isValidPassWord) {
                    password.setError("Password must be at least 6 characters", drawable);
                    hasError = true;
                }
                if (!hasError) {
                    Log.d("email", inputEmail);
                    Log.d("password", password.getText().toString());
                    authenticateUser(inputEmail, password.getText().toString());
                }
            }
        });
    }





}