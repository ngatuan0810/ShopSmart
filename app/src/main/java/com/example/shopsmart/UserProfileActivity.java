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

import com.example.shopsmart.Adapter.ProductAdapter;
import com.example.shopsmart.Domain.Product;
import com.example.shopsmart.utils.ProductUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProfileActivity extends AppCompatActivity {
    ViewFlipper flipper;
    FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProductAdapter adapter;
    private List<Product> filteredList;
    int[] layouts = new int[] {R.layout.my_interest, R.layout.my_watched, R.layout.my_review, R.layout.my_coupon, R.layout.my_notification};

    // Mảng tĩnh lưu các ID sản phẩm đã xem
    public static Set<String> watchedProductIds = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Get the current user's profile
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

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

        recyclerView = findViewById(R.id.watched_products_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = ProductUtils.loadProductsFromJson(this);

        // Lọc danh sách sản phẩm dựa trên các ID đã xem
        filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (watchedProductIds.contains(product.getId())) {
                filteredList.add(product);
            }
        }
        for (Product product : productList) {
            int count = 0;
            if (product.getJbhifi_fee() > 0) count++;
            if (product.getOfficework_fee() > 0) count++;
            if (product.getGoodguys_fee() > 0) count++;
            if (product.getBigw_fee() > 0) count++;
            if (product.getBrand_fee() > 0) count++;
            product.setNumber_retailers(count);
        }
        adapter = new ProductAdapter(filteredList);
        recyclerView.setAdapter(adapter);
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
