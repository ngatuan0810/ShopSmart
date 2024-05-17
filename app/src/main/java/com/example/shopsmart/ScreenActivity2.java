package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ScreenActivity2 extends AppCompatActivity {
    ViewPager viewPager;
    private BottomNavigationView navView;

    int images[] = {R.drawable.image_3, R.drawable.image_2, R.drawable.image_1};
    int currentPage = 0;
    Timer timer;
    LinearLayout indicatorContainer;
    List<Item> lstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        navView = findViewById(R.id.nav);
        Menu menu = navView.getMenu();
        MenuItem profileItem = menu.findItem(R.id.me);
        profileItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ScreenActivity1.this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            }
        });

        ImageView profileIcon = findViewById(R.id.imageView15);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenActivity1.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout = findViewById(R.id.linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScreenActivity1.this, SearchActivity.class));
            }
        });
        lstItem = new ArrayList<>();
        lstItem.add(new Item(R.drawable.image_4));
        lstItem.add(new Item(R.drawable.image_5));
        lstItem.add(new Item(R.drawable.image_6));
        lstItem.add(new Item(R.drawable.image_7));
        lstItem.add(new Item(R.drawable.image_8));
        lstItem.add(new Item(R.drawable.image_9));
        lstItem.add(new Item(R.drawable.image_10));
        lstItem.add(new Item(R.drawable.image_11));

        RecyclerView myrv = findViewById(R.id.recycler_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstItem);
        myrv.setLayoutManager(new GridLayoutManager(this, 4));
        myrv.setAdapter(myAdapter); // Add this line to set the adapter

        viewPager = findViewById(R.id.viewPaper);
        viewPager.setAdapter(new SliderAdapter(images, ScreenActivity2.this));

        indicatorContainer = findViewById(R.id.indicatorContainer);
        createIndicators();

        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPage == images.length - 1) {
                currentPage = 0;
            } else {
                currentPage++;
            }
            viewPager.setCurrentItem(currentPage, true);
        };

        // Schedule timer task to switch images every 2500 milliseconds (2.5 seconds)
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2500, 2500);

        // Add a ViewPager listener to update indicators when page changes
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer when the activity is destroyed to avoid memory leaks
        if (timer != null) {
            timer.cancel();
        }
    }

    // Method to create indicators dynamically
    @SuppressLint("UseCompatLoadingForDrawables")
    private void createIndicators() {
        for (int i = 0; i < images.length; i++) {
            ImageView indicator = new ImageView(this);
            indicator.setImageDrawable(getResources().getDrawable(R.drawable.indicator_inactive)); // Set inactive indicator drawable
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0); // Adjust margins as needed
            indicator.setLayoutParams(params);
            indicatorContainer.addView(indicator);
        }
        updateIndicators(0); // Set the initial indicator to active
    }

    // Method to update indicators based on current page
    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            ImageView indicator = (ImageView) indicatorContainer.getChildAt(i);
            indicator.setImageDrawable(getResources().getDrawable(
                    i == position ? R.drawable.indicator_active : R.drawable.indicator_inactive
            ));
        }
    }
}

