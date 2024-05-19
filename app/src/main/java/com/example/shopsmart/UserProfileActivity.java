package com.example.shopsmart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsmart.Domain.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    ViewFlipper flipper;
    FirebaseAuth firebaseAuth;
    List<Product> favorites;
    int[] layouts = new int[] {R.layout.my_interest, R.layout.my_watched, R.layout.my_review, R.layout.my_coupon, R.layout.my_notification};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Get the current user's profile
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        if (args != null) {
            favorites = (List<Product>) args.getSerializable("FAV");
        }

        BottomNavigationView navView = findViewById(R.id.nav);
        Menu menu = navView.getMenu();
        MenuItem homeIcon = menu.findItem(R.id.home);
        MenuItem productIcon = menu.findItem(R.id.product);

        MenuItem meIcon = menu.findItem(R.id.me);
        meIcon.setChecked(true);
        homeIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(UserProfileActivity.this, ScreenActivity2.class);
                startActivity(intent);
                return false;
            }
        });
        productIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(UserProfileActivity.this, ProductActivity1.class);
                startActivity(intent);
                return false;
            }
        });
        // Display username in profile
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
        LinearLayout seller1 = findViewById(R.id.JBHifi);
        LinearLayout seller2 = findViewById(R.id.GoodGuys);
        ImageView noticeButton = findViewById(R.id.imageView37);
        // URL redirection for sellers
        seller1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.jbhifi.com.au"));
                startActivity(intent);
            }
        });
        seller2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.thegoodguys.com.au"));
                startActivity(intent);
            }
        });
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, MyInterestActivity.class);
                if (favorites != null) {
                    Bundle favorite = new Bundle();
                    favorite.putSerializable("FAV", (Serializable) favorites);
                    intent.putExtra("BUNDLE", favorite);
                }
                startActivity(intent);
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
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemNav(5, R.id.imageView5);
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
