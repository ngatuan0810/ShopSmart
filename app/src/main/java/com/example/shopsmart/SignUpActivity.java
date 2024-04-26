package com.example.shopsmart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        setupSignupContent();

        ImageView imageView = findViewById(R.id.imageView10);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnSignUp = findViewById(R.id.button3);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, ScreenActivity1.class);
                startActivity(intent);
            }
        });

        TextView haveAccount = findViewById(R.id.textView16);
        TextView signIn = findViewById(R.id.textView17);

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
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
