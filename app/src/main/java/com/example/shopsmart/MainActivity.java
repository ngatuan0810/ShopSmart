package com.example.shopsmart;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//
//        Intent intent = new Intent(this, IntroActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
        /*
        Intent intent = new Intent(this, ScreenActivity1.class);
        startActivity(intent);
         */
    }
}

