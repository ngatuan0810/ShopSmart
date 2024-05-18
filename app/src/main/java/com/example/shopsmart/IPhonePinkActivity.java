package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.blogc.android.views.ExpandableTextView;

public class IPhonePinkActivity extends AppCompatActivity {
    private boolean isExpanded = false;
    private ViewPager viewPager;
    private ImageView imageView;
    private List<Uri> imageUris = new ArrayList<>();
    private IphoneAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    private LinearLayout indicatorContainer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iphone_pink);

        // Nhận dữ liệu sản phẩm từ Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        String productTitle = intent.getStringExtra("productTitle");
        String productBrand = intent.getStringExtra("productBrand");
        String productType = intent.getStringExtra("productType");
        double productPrice = intent.getDoubleExtra("productPrice", 0.0);
        float productScore = intent.getFloatExtra("productScore", 0.0f);
        int numberRetailers = intent.getIntExtra("numberRetailers", 0);
        double jbhifiFee = intent.getDoubleExtra("jbhifiFee", 0.0);
        double officeworkFee = intent.getDoubleExtra("officeworkFee", 0.0);
        double goodguysFee = intent.getDoubleExtra("goodguysFee", 0.0);
        double bigwFee = intent.getDoubleExtra("bigwFee", 0.0);
        double brandFee = intent.getDoubleExtra("brandFee", 0.0);

        // Hiển thị dữ liệu sản phẩm
        TextView titleTextView = findViewById(R.id.productTitle);
        TextView scoreTextView = findViewById(R.id.productScore);
        TextView retailersTextView = findViewById(R.id.number_retailer);
        TextView jbhifiFeeTextView = findViewById(R.id.jbhifi_fee);
        TextView officeworkFeeTextView = findViewById(R.id.officework_fee);
        TextView goodguysFeeTextView = findViewById(R.id.goodguys_fee);
        TextView bigwFeeTextView = findViewById(R.id.bigw_fee);
        TextView brandFeeTextView = findViewById(R.id.brand_fee);

        titleTextView.setText(productTitle);
        scoreTextView.setText(String.valueOf(productScore));
        retailersTextView.setText(String.valueOf(numberRetailers));
        jbhifiFeeTextView.setText(String.format("$%,.2f", jbhifiFee));
        officeworkFeeTextView.setText(String.format("$%,.2f", officeworkFee));
        goodguysFeeTextView.setText(String.format("$%,.2f", goodguysFee));
        bigwFeeTextView.setText(String.format("$%,.2f", bigwFee));
        brandFeeTextView.setText(String.format("$%,.2f", brandFee));

        setupTextClickListener(findViewById(R.id.textView43), findViewById(R.id.imageView66), findViewById(R.id.underline43));
        setupTextClickListener(findViewById(R.id.textView45), findViewById(R.id.imageView72), findViewById(R.id.underline45));
        setupTextClickListener(findViewById(R.id.textView46), findViewById(R.id.imageView73), findViewById(R.id.underline46));
        setupTextClickListener(findViewById(R.id.textView47), findViewById(R.id.imageView59), findViewById(R.id.underline47));

        viewPager = findViewById(R.id.viewPager);
        indicatorContainer1 = findViewById(R.id.indicatorContainer1);

        // Fetch images from Firebase
        fetchImagesFromFirebase(productId);

        // Auto-scroll ViewPager
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPage == imageUris.size() - 1) {
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

    }

    private void fetchImagesFromFirebase(String productId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(productId);

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUris.add(uri);
                            if (adapter == null) {
                                adapter = new IphoneAdapter(imageUris, IPhonePinkActivity.this);
                                viewPager.setAdapter(adapter);
                                createIndicators();
                            } else {
                                adapter.notifyDataSetChanged();
                                createIndicators();
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors
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
        indicatorContainer1.removeAllViews();
        for (int i = 0; i < imageUris.size(); i++) {
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
