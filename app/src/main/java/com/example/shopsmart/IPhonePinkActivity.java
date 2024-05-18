package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Timer;
import java.util.TimerTask;

import at.blogc.android.views.ExpandableTextView;

public class IPhonePinkActivity extends AppCompatActivity {
    private boolean isExpanded = false;
    ViewPager viewPager;
    ImageView imageView;
    int[] images = {R.drawable.image_12, R.drawable.image_13, R.drawable.image_14, R.drawable.image_15};

    int currentPage = 0;
    Timer timer;
    LinearLayout indicatorContainer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iphone_pink);

        setupTextClickListener(findViewById(R.id.textView43), findViewById(R.id.imageView66), findViewById(R.id.underline43));
        setupTextClickListener(findViewById(R.id.textView45), findViewById(R.id.imageView72), findViewById(R.id.underline45));
        setupTextClickListener(findViewById(R.id.textView46), findViewById(R.id.imageView73), findViewById(R.id.underline46));
        setupTextClickListener(findViewById(R.id.textView47), findViewById(R.id.imageView59), findViewById(R.id.underline47));
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new IphoneAdapter(images, IPhonePinkActivity.this));

        // Setup indicators
        indicatorContainer1 = findViewById(R.id.indicatorContainer1);
        createIndicators();



        // Auto-scroll ViewPager
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPage == images.length - 1) {
                currentPage = 0;
            } else {
                currentPage++;
            }
            viewPager.setCurrentItem(currentPage, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2500, 2500);

        // Add ViewPager listener
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

        // Set click listener for "Read More" button
        TableLayout tableLayout = findViewById(R.id.table_layout);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);
            if (i >= 4) {
                child.setVisibility(View.GONE);
            }
        }

        Button moreDetailsButton = findViewById(R.id.button_toggle1);
        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    collapseTable();
                } else {
                    expandTable();
                }
            }
        });

        Button toggleButton = findViewById(R.id.button_toggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTextExpansion();
            }
        });

        imageView = findViewById(R.id.imageView67);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.jbhifi.com.au/products/apple-iphone-15-128gb-pink");
            }
        });
        imageView = findViewById(R.id.imageView68);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.officeworks.com.au/shop/officeworks/p/iphone-15-128gb-pink-ipp15clr2");
            }
        });
        imageView = findViewById(R.id.imageView69);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.thegoodguys.com.au/apple-iphone-15-128gb-pink-mtp13zpa");
            }
        });
        imageView = findViewById(R.id.imageView70);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.bigw.com.au/product/apple-iphone-15-128gb-pink/p/292153");
            }
        });
        imageView = findViewById(R.id.imageView71);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.apple.com/shop/buy-iphone/iphone-15");
            }
        });
        imageView = findViewById(R.id.imageView82);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.apple.com/shop/buy-iphone/iphone-15-pro");
            }
        });
        imageView = findViewById(R.id.imageView83);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.apple.com/shop/buy-iphone/iphone-15-pro");
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav);
        Menu menu = navView.getMenu();
        MenuItem homeIcon = menu.findItem(R.id.home);
        MenuItem meIcon = menu.findItem(R.id.me);
        MenuItem productIcon = menu.findItem(R.id.product);
        homeIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(IPhonePinkActivity.this, ScreenActivity2.class);
                startActivity(intent);
                return true;
            }
        });
        meIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(IPhonePinkActivity.this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            }
        });
        productIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(IPhonePinkActivity.this, ProductActivity1.class);
                startActivity(intent);
                return true;
            }
        });

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createIndicators() {
        for (int i = 0; i < images.length; i++) {
            ImageView indicator = new ImageView(this);
            indicator.setImageDrawable(getResources().getDrawable(R.drawable.iphone_indicator_active));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            indicatorContainer1.addView(indicator);
        }
        updateIndicators(0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorContainer1.getChildCount(); i++) {
            ImageView indicator = (ImageView) indicatorContainer1.getChildAt(i);
            indicator.setImageDrawable(getResources().getDrawable(
                    i == position ? R.drawable.iphone_indicator_active : R.drawable.iphone_indicator_inactive
            ));
        }
    }

    private void toggleTextExpansion() {
        ExpandableTextView expandableTextView = findViewById(R.id.expandableTextView);
        expandableTextView.toggle(); // Toggle text expansion

        Button toggleButton = findViewById(R.id.button_toggle);
        if (isExpanded) {
            toggleButton.setText(R.string.more_details);
        } else {
            toggleButton.setText(R.string.less_details);
        }
        isExpanded = !isExpanded; // Update flag
    }

    @SuppressLint("SetTextI18n")
    private void expandTable() {
        TableLayout tableLayout = findViewById(R.id.table_layout);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);
            child.setVisibility(View.VISIBLE); // Toggle visibility of all rows
        }
        Button moreDetailsButton = findViewById(R.id.button_toggle1);
        moreDetailsButton.setText("Less Details"); // Change button text
        isExpanded = true; // Update flag
    }

    @SuppressLint("SetTextI18n")
    private void collapseTable() {
        TableLayout tableLayout = findViewById(R.id.table_layout);
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);
            if (i >= 4) {
                child.setVisibility(View.GONE); // Hide additional rows beyond the first four
            }
        }
        Button moreDetailsButton = findViewById(R.id.button_toggle1);
        moreDetailsButton.setText("More Details"); // Change button text
        isExpanded = false; // Update flag
    }

    private TextView lastClickedTextView;
    private View lastClickedUnderlineView;

    private void setupTextClickListener(final TextView textView, final View targetView, final View underlineView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToTarget(targetView);
                toggleUnderlineVisibility(textView, underlineView);
            }
        });
    }

    private void toggleUnderlineVisibility(TextView textView, View underlineView) {
        if (lastClickedTextView != null && lastClickedUnderlineView != null) {
            lastClickedUnderlineView.setVisibility(View.INVISIBLE);
        }
        underlineView.setVisibility(View.VISIBLE);
        lastClickedTextView = textView;
        lastClickedUnderlineView = underlineView;
    }

    private void scrollToTarget(View targetView) {
        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, targetView.getTop());
    }
}
