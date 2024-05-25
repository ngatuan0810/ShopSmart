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
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.shopsmart.Adapter.ProductAdapter;
import com.example.shopsmart.Domain.Product;
import com.example.shopsmart.utils.ProductUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenActivity2 extends AppCompatActivity {
    ViewPager viewPager;
    ImageView imageView;
    private BottomNavigationView navView;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> filteredList;
    private List<Product> productList;
    private LinearLayout linear;
    int images[] = {R.drawable.image_3, R.drawable.image_2, R.drawable.image_1};
    int currentPage = 0;
    Timer timer;
    LinearLayout indicatorContainer;
    List<Item> lstItem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        navView = findViewById(R.id.nav);
        Menu menu = navView.getMenu();
        MenuItem meIcon = menu.findItem(R.id.me);
        MenuItem productIcon = menu.findItem(R.id.product);
        meIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ScreenActivity2.this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            }
        });
        productIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ScreenActivity2.this, ProductActivity1.class);
                startActivity(intent);
                return true;
            }
        });

        ImageView profileIcon = findViewById(R.id.imageView15);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenActivity2.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView likeIcon = findViewById(R.id.imageView19);
        likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenActivity2.this, MyInterestActivity.class);
                startActivity(intent);
            }
        });

        ImageView notificationIcon = findViewById(R.id.imageView20);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenActivity2.this, MyNotificationActivity.class);
                startActivity(intent);
            }
        });

        linear = findViewById(R.id.linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenActivity2.this, ProductActivity1.class);
                startActivity(intent);
            }
        });


        imageView = findViewById(R.id.imageView17);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.jbhifi.com.au/products/hp-50r74pa-14-hd-laptop-128gb-intel-celeron");
            }
        });

        imageView = findViewById(R.id.imageView21);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.thegoodguys.com.au");
            }
        });


        lstItem = new ArrayList<>();
        lstItem.add(new Item(R.drawable.image_4, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_5, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_6, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_7, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_8, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_9, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_10, ProductActivity1.class));
        lstItem.add(new Item(R.drawable.image_11, ProductActivity1.class));

        RecyclerView myrv = findViewById(R.id.recycler_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstItem);
        myrv.setLayoutManager(new GridLayoutManager(this, 4));
        myrv.setAdapter(myAdapter);

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

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2500, 2500);

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

        recyclerView = findViewById(R.id.recommend_products);
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

        // Sắp xếp danh sách sản phẩm theo giá từ thấp đến cao
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getMinFee(), p2.getMinFee());
            }
        });

        filteredList = new ArrayList<>(productList);
        adapter = new ProductAdapter(filteredList);
        recyclerView.setAdapter(adapter);
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
            indicator.setImageDrawable(getResources().getDrawable(R.drawable.indicator_inactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            indicatorContainer.addView(indicator);
        }
        updateIndicators(0);
    }

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
