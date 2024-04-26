package com.example.shopsmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in); // Assuming the layout file for LogInActivity is activity_login.xml
        setupLoginContent();

        Button btnSignIn = findViewById(R.id.button2);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ScreenActivity1.class);
                startActivity(intent);
            }
        });

        TextView noAccount = findViewById(R.id.textView9);
        TextView signUp = findViewById(R.id.textView10);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });

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
    }

}