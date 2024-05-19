package com.example.shopsmart;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.Domain.Product;
import com.squareup.picasso.Picasso;
import java.util.List;

public class WatchedProductsAdapter extends RecyclerView.Adapter<WatchedProductsAdapter.ViewHolder> {

    private List<Product> watchedProductsList;
    private Context context;

    public WatchedProductsAdapter(List<Product> watchedProductsList, Context context) {
        this.watchedProductsList = watchedProductsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_watched, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = watchedProductsList.get(position);
        holder.titleTxt.setText(product.getTitle());

        // Sử dụng Picasso để tải hình ảnh từ URL vào ImageView
        Picasso.get().load(product.getImageFolder()).into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, IPhonePinkActivity.class);
            intent.putExtra("productId", product.getId());
            intent.putExtra("productTitle", product.getTitle());
            intent.putExtra("productBrand", product.getBrand());
            intent.putExtra("productType", product.getType());
            intent.putExtra("productPrice", product.getJbhifi_fee());
            intent.putExtra("productScore", product.getScore_rating());
            intent.putExtra("numberRetailers", product.getNumber_retailers());
            intent.putExtra("jbhifiFee", product.getJbhifi_fee());
            intent.putExtra("officeworkFee", product.getOfficework_fee());
            intent.putExtra("goodguysFee", product.getGoodguys_fee());
            intent.putExtra("bigwFee", product.getBigw_fee());
            intent.putExtra("brandFee", product.getBrand_fee());
            intent.putExtra("releaseDate", product.getReleaseDate());
            intent.putExtra("jbhifiLink", product.getJbhifi_link());
            intent.putExtra("officeworkLink", product.getOfficework_link());
            intent.putExtra("goodguysLink", product.getGoodguys_link());
            intent.putExtra("bigwLink", product.getBigw_link());
            intent.putExtra("brandLink", product.getBrand_link());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("specs", product.getSpecs());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return watchedProductsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTxt;
        public ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
