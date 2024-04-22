package com.example.shopsmart;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in); // Assuming the layout file for LogInActivity is activity_login.xml

        // Call the method to set up login content
        setupLoginContent();
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