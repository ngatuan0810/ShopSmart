//BrandsAdapter.java
package com.example.shopsmart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.Domain.Brand;
import com.example.shopsmart.R;
import com.example.shopsmart.ScreenActivity1;

import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.BrandViewHolder> {

    private List<Brand> brandList;

    public BrandsAdapter(List<Brand> brandList) {
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brandList.get(position);
        holder.bind(brand);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public void updateBrands(List<Brand> newBrands) {
        brandList.clear();
        brandList.addAll(newBrands);
        notifyDataSetChanged();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        private Button brandNameBtn;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandNameBtn = (Button) itemView.findViewById(R.id.brand_button);
        }

        public void bind(Brand brand) {
            brandNameBtn.setText(brand.getBrandName());
            // Add click listener to handle filtering by brand
            brandNameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String brandName = brand.getBrandName();
                    ((ScreenActivity1) itemView.getContext()).filterProductsByBrand(brandName);

                }
            });
        }
    }
}
