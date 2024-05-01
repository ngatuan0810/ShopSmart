package com.example.shopsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {
    ViewGroup mainScreen;
    View gridLayout;

    View middleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        LinearLayout likedButton = findViewById(R.id.likedButton);
        LinearLayout watchedButton = findViewById(R.id.watchedButton);
        LinearLayout reviewedButton = findViewById(R.id.reviewedButton);
        LinearLayout couponButton = findViewById(R.id.couponButton);
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(R.layout.my_interest);
            }
        });
        watchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(R.layout.my_watched);
            }
        });
        reviewedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(R.layout.my_review);
            }
        });
        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(R.layout.my_coupon);
            }
        });
    }
    void handleItemNav(int id) {
        mainScreen = findViewById(R.id.screenMain);
        gridLayout = findViewById(R.id.gridLayout);
        middleBar = findViewById(R.id.middleBar);
        mainScreen.removeView(middleBar);
        mainScreen.removeView(gridLayout);
        middleBar = getLayoutInflater().inflate(id, mainScreen, false);
        mainScreen.addView(middleBar, 1);

        ImageView backIcon = findViewById(R.id.imageView59);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackNav();
            }
        });
    }
    void handleBackNav() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
