//ProductActivity1.java
package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsmart.Adapter.BrandsAdapter;
import com.example.shopsmart.Adapter.ProductAdapter;
import com.example.shopsmart.Domain.Brand;
import com.example.shopsmart.Domain.Product;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import android.widget.SeekBar;

import com.example.shopsmart.utils.ProductUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import com.example.shopsmart.Domain.Product;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;

public class ProductActivity1 extends AppCompatActivity {

    enum FilterType {
        RELEVANT,
        LATEST,
        PRICE
    }

    private EditText searchItemInput;
    private ImageButton searchButton;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBrand;
    private ProductAdapter adapter;
    private List<Product> productList;
    private List<Product> filteredList;
    private TextView results_found;
    private boolean isAscending = true;
    private FilterType currentFilter = FilterType.RELEVANT;
    private BrandsAdapter brandsAdapter;
    private SeekBar priceSeekBar;
    private TextView minPriceTextView;
    private TextView maxPriceTextView;

    private FirebaseStorage storage;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();

        searchItemInput = findViewById(R.id.search_item_input);
        searchButton = findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.recyclerView);
        results_found = findViewById(R.id.results_found);
        recyclerViewBrand = findViewById(R.id.brands_recycler);
        priceSeekBar = findViewById(R.id.price_filter_seekbar);
        minPriceTextView = findViewById(R.id.min_price);
        maxPriceTextView = findViewById(R.id.max_price);

        // Initialize clear filter button
        Button clearFilterBtn = findViewById(R.id.clear_filter_btn);

//        productList = new ArrayList<>();
//        productList.add(new Product( "p01", "Apple iPhone 15 128GB (Pink)",  4.8F, "Apple", "Smart Phone", 1299, 0, 1299, 1299, 1599, "23/09/2023"));
//        productList.add(new Product( "p02", "Samsung Galaxy S22 Ultra 256GB (Black)", 4.9F, "Samsung", "Smart Phone", 1499, 1599, 1599, 1499, 1599, "14/02/2024"));
//        productList.add(new Product("p03", "Google Pixel 8 5G 128GB (ROSE)", 4.5F, "Google", "Smart Phone", 899, 899, 899, 899, 899, "20/11/2023"));
//        productList.add(new Product("p04", "OPPO A18 128GB (Glowing Blue)", 4.2F, "Oppo", "Smart Phone", 499, 489, 589, 0, 499, "18/04/2023"));
//        productList.add(new Product("p05", "OPPO A18 128GB (Glowing Blue)", 4.2F, "Oppo", "Smart Phone", 499, 499, 0, 499, 499, "08/06/2023"));
//        productList.add(new Product("p06", "OPPO A18 128GB (Glowing Blue)", 4.2F, "Oppo", "Smart Phone", 445, 499, 499, 499, 499, "29/12/2023"));
//        productList.add(new Product("p06", "OPPO A18 128GB (Glowing Blue)", 4.2F, "Oppo", "Smart Phone", 1299, 1399, 1299, 1299, 1299, "29/12/2023"));
//
//        // Đọc dữ liệu từ file JSON
        productList = ProductUtils.loadProductsFromJson(this);
//
//        // Push dữ liệu lên Firebase
        pushDataToFirebase(productList);

        for (Product product : productList) {
            int count = 0;
            if (product.getJbhifi_fee() > 0) count++;
            if (product.getOfficework_fee() > 0) count++;
            if (product.getGoodguys_fee() > 0) count++;
            if (product.getBigw_fee() > 0) count++;
            if (product.getBrand_fee() > 0) count++;
            product.setNumber_retailers(count);
        }

        for (Product product : productList) {
            product.setImageId(getResources().getIdentifier(Product.getImageResourceName(product.getId()), "drawable", getPackageName()));
        }

        filteredList = new ArrayList<>(productList);

        recyclerViewBrand.setLayoutManager(new GridLayoutManager(this, 3));
        brandsAdapter = new BrandsAdapter(new ArrayList<>());
        recyclerViewBrand.setAdapter(brandsAdapter);
        updateBrandsAdapter();

        currentFilter = FilterType.RELEVANT; // Thiết lập mặc định là RELEVANT
        updateUnderlineVisibility();
        updatePriceSeekBarRange();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
                updateBrandsAdapter();
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav);
        Menu menu = navView.getMenu();
        MenuItem homeIcon = menu.findItem(R.id.home);
        MenuItem meIcon = menu.findItem(R.id.me);
        MenuItem productIcon = menu.findItem(R.id.product);
        productIcon.setChecked(true);
        homeIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ProductActivity1.this, ScreenActivity2.class);
                startActivity(intent);
                return true;
            }
        });
        meIcon.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ProductActivity1.this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            }
        });


        // Các sự kiện click cho các nút lọc
        Button latestFilterBtn = findViewById(R.id.latest_filter_btn);
        Button relevantFilterBtn = findViewById(R.id.relevant_filter_btn);
        Button priceFilterBtn = findViewById(R.id.price_filter_btn);
        ImageButton filterButton = findViewById(R.id.filter_button);
        final ConstraintLayout filterSlideBar = findViewById(R.id.filter_slide_bar);
        ImageButton backButton = findViewById(R.id.back_btn);

        ImageButton searchFilterBtn = findViewById(R.id.search_filter_btn);
        searchFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFiltering();
                updateBrandsAdapter();
                Transition slideOutTransition = TransitionInflater.from(ProductActivity1.this)
                        .inflateTransition(R.transition.slide_out_right);
                TransitionManager.beginDelayedTransition(filterSlideBar, slideOutTransition);
                filterSlideBar.setVisibility(View.GONE);
            }
        });

        // Tìm giá thấp nhất và cao nhất và cập nhật SeekBar và TextView tương ứng
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = seekBar.getMin();
                int max = seekBar.getMax();
                double ratio = (double) progress / (double) max;
                double maxPrice = min + ratio * (max - min);
                maxPriceTextView.setText(String.format("%,.2f", maxPrice));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition slideInTransition = TransitionInflater.from(ProductActivity1.this)
                        .inflateTransition(R.transition.slide_in_right);
                TransitionManager.beginDelayedTransition(filterSlideBar, slideInTransition);
                filterSlideBar.setVisibility(View.VISIBLE);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition slideOutTransition = TransitionInflater.from(ProductActivity1.this)
                        .inflateTransition(R.transition.slide_out_right);
                TransitionManager.beginDelayedTransition(filterSlideBar, slideOutTransition);
                filterSlideBar.setVisibility(View.GONE);
            }
        });

        latestFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortProductsByLatest();
                updateBrandsAdapter();
                currentFilter = FilterType.LATEST;
                updateUnderlineVisibility();
                updatePriceSeekBarRange();
            }
        });

        priceFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortProductsByPrice();
                updateBrandsAdapter();
                currentFilter = FilterType.PRICE;
                updateUnderlineVisibility();
                updatePriceSeekBarRange();
            }
        });

        relevantFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu số lượng sản phẩm ban đầu trong productList
                int initialProductListSize = productList.size();

                // Cập nhật danh sách sản phẩm được lọc và các thành phần khác
//                filteredList.clear();
//                filteredList.addAll(productList);
                updateBrandsAdapter();
                adapter.notifyDataSetChanged();
                currentFilter = FilterType.RELEVANT;
                updateUnderlineVisibility();
                updatePriceSeekBarRange();

                // Kiểm tra xem filteredList có giống với productList không
                if (filteredList.size() == initialProductListSize) {
                    // Nếu có, hiển thị thông báo "Highlighted Items"
                    String searchResultText = "Highlighted Items";
                    results_found.setText(searchResultText);
                } else {
                    // Nếu không, hiển thị số lượng sản phẩm kết quả
                    String searchResultText = filteredList.size() + " Results";
                    results_found.setText(searchResultText);
                }
            }
        });


        // Set onClickListener for the clear filter button
        clearFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
                Transition slideOutTransition = TransitionInflater.from(ProductActivity1.this)
                        .inflateTransition(R.transition.slide_out_right);
                TransitionManager.beginDelayedTransition(filterSlideBar, slideOutTransition);
                filterSlideBar.setVisibility(View.GONE);
            }
        });
        String filterType = getIntent().getStringExtra("filterType");
        if (filterType != null && filterType.equals("Tivi")) {
            filteredList.clear();
            for (Product product : productList) {
                if (product.getType().equalsIgnoreCase("Tivi")) {
                    filteredList.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else if (filterType != null && filterType.equals("Smart Phone")) {
            filteredList.clear();
            for (Product product : productList) {
                if (product.getType().equalsIgnoreCase("Smart Phone")) {
                    filteredList.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else if (filterType != null && filterType.equals("Earphones")) {
            filteredList.clear();
            for (Product product : productList) {
                if (product.getType().equalsIgnoreCase("Earphones")) {
                    filteredList.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else if (filterType != null && filterType.equals("Fridge")) {
            filteredList.clear();
            for (Product product : productList) {
                if (product.getType().equalsIgnoreCase("Fridge")) {
                    filteredList.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else if (filterType != null && filterType.equals("Laptop")) {
            filteredList.clear();
            for (Product product : productList) {
                if (product.getType().equalsIgnoreCase("Laptop")) {
                    filteredList.add(product);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void clearFilters() {
        // Reset the filtered list to the original product list
        filteredList.clear();
        filteredList.addAll(productList);
        adapter.notifyDataSetChanged();

        // Reset current filter to RELEVANT
        currentFilter = FilterType.RELEVANT;
        updateUnderlineVisibility();

        // Reset the search input
        searchItemInput.setText("");

        // Reset the results found text
        String searchResultText = "Highlighted Items";
        results_found.setText(searchResultText);

        // Reset the price seek bar range
        updatePriceSeekBarRange();

        // Update the brands adapter
        updateBrandsAdapter();
    }

    private List<String> getUniqueBrands(List<Product> productList) {
        List<String> uniqueBrands = new ArrayList<>();
        for (Product product : productList) {
            if (!uniqueBrands.contains(product.getBrand())) {
                uniqueBrands.add(product.getBrand());
            }
        }
        return uniqueBrands;
    }

    private void updateBrandsAdapter() {
        List<Brand> brands = new ArrayList<>();
        for (String brandName : getUniqueBrands(filteredList)) {
            brands.add(new Brand(brandName));
        }
        brandsAdapter.updateBrands(brands);
    }

    private void sortProductsByLatest() {
        Collections.sort(filteredList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Objects.requireNonNull(p2.getReleaseDate()).compareTo(p1.getReleaseDate());
            }
        });
        adapter.notifyDataSetChanged();
        String searchResultText = filteredList.size() + " Results - Latest";
        results_found.setText(searchResultText);
    }

    private void sortProductsByPrice() {
        // Sắp xếp danh sách sản phẩm theo giá
        Collections.sort(filteredList, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                // So sánh giá theo trạng thái sắp xếp hiện tại
                if (!isAscending) {
                    return Double.compare(p1.getMinFee(), p2.getMinFee());
                } else {
                    return Double.compare(p2.getMinFee(), p1.getMinFee());
                }
            }
        });
        isAscending = !isAscending;
        // Cập nhật adapter để hiển thị danh sách sản phẩm đã sắp xếp
        adapter.notifyDataSetChanged();

        // Cập nhật văn bản hiển thị trạng thái sắp xếp
        String orderText = isAscending ? "Low to High" : "High to Low";
        String searchResultText = filteredList.size() + " Results - " + orderText + " Price";
        results_found.setText(searchResultText);
    }

    private void performSearch() {
        String query = searchItemInput.getText().toString().trim().toLowerCase();
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(productList);
        } else {
            for (Product product : productList) {
                if (product.getTitle().toLowerCase().contains(query) ||
                        product.getBrand().toLowerCase().contains(query) ||
                        product.getType().toLowerCase().contains(query)) {
                    filteredList.add(product);
                }
            }
        }
        adapter.notifyDataSetChanged();
        String searchResultText = filteredList.size() + " Results for \"" + query + "\"";
        results_found.setText(searchResultText);
        updatePriceSeekBarRange();
    }

    private void updateUnderlineVisibility() {
        ImageView underline1 = findViewById(R.id.underline1);
        ImageView underline2 = findViewById(R.id.underline2);
        ImageView underline3 = findViewById(R.id.underline3);
        underline1.setVisibility(View.INVISIBLE);
        underline2.setVisibility(View.INVISIBLE);
        underline3.setVisibility(View.INVISIBLE);
        switch (currentFilter) {
            case RELEVANT:
                underline1.setVisibility(View.VISIBLE);
                break;
            case LATEST:
                underline2.setVisibility(View.VISIBLE);
                break;
            case PRICE:
                underline3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void filterProductsByBrand(String brandName) {
        filteredList.clear();
        for (Product product : productList) {
            if (product.getBrand().equalsIgnoreCase(brandName)) {
                filteredList.add(product);
            }
        }
        adapter.notifyDataSetChanged();
        String searchResultText = filteredList.size() + " Results for \"" + brandName + "\"";
        results_found.setText(searchResultText);
        updatePriceSeekBarRange();
    }

    private void updatePriceSeekBarRange() {
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        for (Product product : filteredList) { // Sử dụng filteredList thay vì productList
            double price = product.getMinFee();
            minPrice = Math.min(minPrice, price);
            maxPrice = Math.max(maxPrice, price);
        }
        priceSeekBar.setMax((int) maxPrice); // Đặt giá trị tối đa
        priceSeekBar.setProgress((int) minPrice); // Đặt giá trị tối thiểu
        minPriceTextView.setText(String.format("%,.2f", minPrice)); // Hiển thị giá trị tối thiểu
        maxPriceTextView.setText(String.format("%,.2f", maxPrice)); // Hiển thị giá trị tối đa
    }

    // Phương thức để thực hiện việc lọc sản phẩm dựa trên giá trị của SeekBar
    private void performFiltering() {
        double maxPrice = Double.parseDouble(maxPriceTextView.getText().toString().replaceAll("[^\\d.]", ""));
        double minPrice = Double.parseDouble(minPriceTextView.getText().toString().replaceAll("[^\\d.]", ""));
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : filteredList) {
            if (product.getMinFee() <= maxPrice && product.getMinFee() >= minPrice ) {
                filteredProducts.add(product);
            }
        }
        filteredList.clear();
        filteredList.addAll(filteredProducts);
        adapter.notifyDataSetChanged();
        String searchResultText = filteredList.size() + " Results";
        results_found.setText(searchResultText);
        updateBrandsAdapter();
    }
    private void pushDataToFirebase(List<Product> productList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");

        for (Product product : productList) {
            myRef.child(product.getId()).setValue(product);
        }
    }


}

