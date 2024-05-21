package com.example.shopsmart;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.Adapter.ProductAdapter;
import com.example.shopsmart.Domain.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MyInterestActivity extends AppCompatActivity {

    private List<Product> interests;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_interest);
        // Get favorite data
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        if (args != null) {
            RecyclerView recyclerView = findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            interests = (List<Product>) args.getSerializable("FAV");
            adapter = new ProductAdapter(interests);
            recyclerView.setAdapter(adapter);
        }
        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInterestActivity.this, ScreenActivity2.class);
                startActivity(intent);
            }
        });
    }
}