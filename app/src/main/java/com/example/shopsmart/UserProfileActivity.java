package com.example.shopsmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {
    ViewFlipper flipper;
    FirebaseAuth firebaseAuth;
    int[] layouts = new int[] {R.layout.my_interest, R.layout.my_watched, R.layout.my_review, R.layout.my_coupon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView userName = findViewById(R.id.textView21);
        flipper = findViewById(R.id.flipper);
        if (user != null) {
            userName.setText(user.getDisplayName());
        }
        for (int layout : layouts) {
            View childView = getLayoutInflater().inflate(layout, null);
            flipper.addView(childView);
        }
        LinearLayout likedButton = findViewById(R.id.likedButton);
        LinearLayout watchedButton = findViewById(R.id.watchedButton);
        LinearLayout reviewedButton = findViewById(R.id.reviewedButton);
        LinearLayout couponButton = findViewById(R.id.couponButton);
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(1, R.id.imageView1);
            }
        });
        watchedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(2, R.id.imageView2);
            }
        });
        reviewedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(3, R.id.imageView3);
            }
        });
        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(4, R.id.imageView4);
            }
        });
        ImageView logOut = findViewById(R.id.imageView36);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(UserProfileActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
    void handleItemNav(int id, int back) {
        flipper.setDisplayedChild(id);
        ImageView backIcon = findViewById(back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.setDisplayedChild(0);
            }
        });
    }
}
