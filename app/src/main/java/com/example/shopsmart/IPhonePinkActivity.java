package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TableRow;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.shopsmart.Adapter.ProductAdapter;
import com.example.shopsmart.utils.ProductUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.blogc.android.views.ExpandableTextView;
import com.example.shopsmart.Domain.Product;

public class IPhonePinkActivity extends AppCompatActivity {
    private boolean isExpanded = false;
    private ViewPager viewPager;
    private ImageView imageView;
    private List<Uri> imageUris = new ArrayList<>();
    private IphoneAdapter adapter;
    private int currentPage = 0;
    private Timer timer;
    private LinearLayout indicatorContainer1;
    private Product product;
    private RecyclerView recyclerView;
    private ProductAdapter adapterProduct;
    private List<Product> filteredList;
    private List<Product> productList;

    @SuppressLint("MissingInflatedId")
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
        String description = intent.getStringExtra("description");
        String specs = intent.getStringExtra("specs");
        String releaseDate = intent.getStringExtra("releaseDate");
        double minFee = intent.getDoubleExtra("minFee", 0.0);

        product = (Product) intent.getSerializableExtra("product"); // Assuming Product implements Serializable


        // Hiển thị dữ liệu sản phẩm
        TextView titleTextView = findViewById(R.id.productTitle);
        TextView titleTextView1 = findViewById(R.id.subproductTitle1);
        TextView titleTextView2 = findViewById(R.id.subproductTitle2);
        TextView titleTextView3 = findViewById(R.id.subproductTitle3);
        TextView titleTextView4 = findViewById(R.id.subproductTitle4);
        TextView titleTextView5 = findViewById(R.id.subproductTitle5);
        ExpandableTextView  descriptionView = findViewById(R.id.expandableTextView);


        TextView scoreTextView = findViewById(R.id.productScore);
        TextView retailersTextView = findViewById(R.id.number_retailer);
        TextView jbhifiFeeTextView = findViewById(R.id.jbhifi_fee);
        TextView officeworkFeeTextView = findViewById(R.id.officework_fee);
        TextView goodguysFeeTextView = findViewById(R.id.goodguys_fee);
        TextView bigwFeeTextView = findViewById(R.id.bigw_fee);
        TextView brandFeeTextView = findViewById(R.id.brand_fee);

        descriptionView.setText(description);
        titleTextView.setText(productTitle);
        titleTextView1.setText(productTitle);
        titleTextView2.setText(productTitle);
        titleTextView3.setText(productTitle);
        titleTextView4.setText(productTitle);
        titleTextView5.setText(productTitle);
        scoreTextView.setText(String.valueOf(productScore));
        retailersTextView.setText(String.valueOf(numberRetailers));
        jbhifiFeeTextView.setText(String.format("$%,.0f", jbhifiFee));
        officeworkFeeTextView.setText(String.format("$%,.0f", officeworkFee));
        goodguysFeeTextView.setText(String.format("$%,.0f", goodguysFee));
        bigwFeeTextView.setText(String.format("$%,.0f", bigwFee));
        brandFeeTextView.setText(String.format("$%,.0f", brandFee));

        // Ẩn các ConstraintLayout nếu phí bằng 0.0
        ConstraintLayout jbhifiLayout = findViewById(R.id.jbhifi_retailer);
        ConstraintLayout officeworkLayout = findViewById(R.id.officework_retailer);
        ConstraintLayout goodguysLayout = findViewById(R.id.goodguys_retailer);
        ConstraintLayout bigwLayout = findViewById(R.id.bigw_retailer);

        if (jbhifiFee == 0.0) {
            jbhifiLayout.setVisibility(View.GONE);
        }
        if (officeworkFee == 0.0) {
            officeworkLayout.setVisibility(View.GONE);
        }
        if (goodguysFee == 0.0) {
            goodguysLayout.setVisibility(View.GONE);
        }
        if (bigwFee == 0.0) {
            bigwLayout.setVisibility(View.GONE);
        }

        ImageView jbhifiTag = findViewById(R.id.supposed_tag_jbhifi);
        ImageView officeworkTag = findViewById(R.id.supposed_tag_officework);
        ImageView goodguysTag = findViewById(R.id.supposed_tag_goodguys);
        ImageView bigwTag = findViewById(R.id.supposed_tag_bigw);
        ImageView brandTag = findViewById(R.id.supposed_tag_brand);
        if (jbhifiFee == minFee) {
            jbhifiTag.setVisibility(View.VISIBLE);
        }
        if (officeworkFee == minFee) {
            officeworkTag.setVisibility(View.VISIBLE);
        }
        if (goodguysFee == minFee) {
            goodguysTag.setVisibility(View.VISIBLE);
        }
        if (bigwFee == minFee) {
            bigwTag.setVisibility(View.VISIBLE);
        }
        if (brandFee == minFee) {
            brandTag.setVisibility(View.VISIBLE);
        }



        TableLayout tableLayout = findViewById(R.id.table_layout);
        fillTableLayoutWithSpecs(tableLayout, specs);


        //setting up click listeners for text views and managing associated image views and underlines
        setupTextClickListener(findViewById(R.id.textView43), findViewById(R.id.imageView66), findViewById(R.id.underline43));
        setupTextClickListener(findViewById(R.id.textView45), findViewById(R.id.imageView72), findViewById(R.id.underline45));
        setupTextClickListener(findViewById(R.id.textView46), findViewById(R.id.imageView73), findViewById(R.id.underline46));
        setupTextClickListener(findViewById(R.id.textView47), findViewById(R.id.imageView59), findViewById(R.id.underline47));

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new IphoneAdapter(imageUris, IPhonePinkActivity.this));
        indicatorContainer1 = findViewById(R.id.indicatorContainer1);
        createIndicators();

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){}

            @Override
            public void onPageSelected(int position) {
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Set click listener for "Read More" button

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
        String jbhifiLink = intent.getStringExtra("jbhifiLink");
        String officeworkLink = intent.getStringExtra("officeworkLink");
        String goodguysLink = intent.getStringExtra("goodguysLink");
        String bigwLink = intent.getStringExtra("bigwLink");
        String brandLink = intent.getStringExtra("brandLink");
        imageView = findViewById(R.id.imageView67);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(jbhifiLink);
            }
        });
        imageView = findViewById(R.id.imageView68);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(officeworkLink);
            }
        });
        imageView = findViewById(R.id.imageView69);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(goodguysLink);
            }
        });
        imageView = findViewById(R.id.imageView70);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(bigwLink);
            }
        });
        imageView = findViewById(R.id.imageView71);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl(brandLink);
            }
        });


        imageView = findViewById(R.id.imageView26);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IPhonePinkActivity.this, ScreenActivity2.class);
                startActivity(intent);
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

        // Lưu sản phẩm đã xem vào Firebase
//        saveWatchedProduct(new Product(
//                productId, productTitle, productScore, productBrand, productType, jbhifiFee, officeworkFee, goodguysFee, bigwFee, brandFee,
//                releaseDate, jbhifiLink, goodguysLink, officeworkLink, bigwLink, brandLink, description, specs
//        ));
        // Lưu ID sản phẩm đã xem vào mảng
        UserProfileActivity.watchedProductIds.add(productId);


        recyclerView = findViewById(R.id.similiar_products);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = ProductUtils.loadProductsFromJson(this);
        for (Product product : productList) {
            int count = 0;
            if (product.getJbhifi_fee() > 0) count++;
            if (product.getOfficework_fee() > 0) count++;
            if (product.getGoodguys_fee() > 0) count++;
            if (product.getBigw_fee() > 0) count++;
            if (product.getBrand_fee() > 0) count++;
            product.setNumber_retailers(count);
        }

        // Sắp xếp danh sách sản phẩm tương tự
        filteredList = filterAndSortSimilarProducts(productList, productBrand, productType);
        adapterProduct = new ProductAdapter(filteredList);
        recyclerView.setAdapter(adapterProduct);

        ImageView brandLogo = findViewById(R.id.brand_retailer_logo);
        int brandImageId = getBrandImageId(this, productBrand);
        brandLogo.setImageResource(brandImageId);

    }

//    private void saveWatchedProduct(Product product) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("watched_products").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        myRef.child(product.getId()).setValue(product);
//    }


    private int getBrandImageId(Context context, String brand) {
        String brandLowercase = brand.toLowerCase();
        String resourceName = "drawable/" + brandLowercase;
        return context.getResources().getIdentifier(resourceName, null, context.getPackageName());
    }

    private List<Product> filterAndSortSimilarProducts(List<Product> products, String brand, String type) {
        List<Product> similarProducts = new ArrayList<>();

        // Thêm sản phẩm cùng brand trước
        for (Product product : products) {
            if (product.getBrand().equalsIgnoreCase(brand) && product.getType().equalsIgnoreCase(type)) {
                similarProducts.add(product);
            }
        }
        for (Product product : products) {
            if (product.getBrand().equalsIgnoreCase(brand) && !product.getType().equalsIgnoreCase(type)) {
                similarProducts.add(product);
            }
        }

        // Thêm sản phẩm cùng type nếu không trùng brand
        for (Product product : products) {
            if (!product.getBrand().equalsIgnoreCase(brand) && product.getType().equalsIgnoreCase(type)) {
                similarProducts.add(product);
            }
        }

        // Sắp xếp danh sách sản phẩm tương tự theo giá từ thấp đến cao


        return similarProducts;
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
    private void fillTableLayoutWithSpecs(TableLayout tableLayout, String specs) {
        String[] specsArray = specs.split("\n");
        for (int i = 0; i < specsArray.length; i += 2) {
            String specName = specsArray[i];
            String specValue = (i + 1 < specsArray.length) ? specsArray[i + 1] : "";

            TableRow tableRow = new TableRow(this);
            TextView specNameTextView = new TextView(this);
            specNameTextView.setText(specName);
            specNameTextView.setTextColor(getResources().getColor(R.color.black));
            specNameTextView.setTypeface(null, Typeface.BOLD);
            specNameTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            TextView specValueTextView = new TextView(this);
            specValueTextView.setText(specValue);
            specValueTextView.setTextColor(getResources().getColor(R.color.black));
            specValueTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            tableRow.addView(specNameTextView);
            tableRow.addView(specValueTextView);
            tableLayout.addView(tableRow);
        }
    }
}
