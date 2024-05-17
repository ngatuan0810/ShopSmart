// Apdapter/ProductAdapter.java
package com.example.shopsmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.Domain.Product;
import com.example.shopsmart.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_prod_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTxt;
        private TextView feeTxt;
        private TextView retailersTxt;
        private TextView scoreTxt;
        private ImageView brandImg;
        private ImageView pic;
        private ImageButton favouriteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            retailersTxt = itemView.findViewById(R.id.number_retailers);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            brandImg = itemView.findViewById(R.id.brand_img);
            pic = itemView.findViewById(R.id.pic);
            favouriteButton = itemView.findViewById(R.id.favourite_btn);
        }

        public void bind(Product product) {
            titleTxt.setText(product.getTitle());
            retailersTxt.setText(String.valueOf(product.getNumber_retailers()));
            scoreTxt.setText(String.valueOf(product.getScore_rating()));
            feeTxt.setText(formatPrice(product.getMinFee()));
            int brandImageId = getBrandImageId(itemView.getContext(), product.getBrand());
            brandImg.setImageResource(brandImageId);
            pic.setImageResource(product.getImageId());


            if (product.isFavourite()) {
                favouriteButton.setImageResource(R.drawable.vector); // Change to filled vector drawable
            } else {
                favouriteButton.setImageResource(R.drawable.vector_3); // Change to outline vector drawable
            }

            // Set onClickListener for the favourite button
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle the favourite status
                    product.setFavourite(!product.isFavourite());

                    // Change the image of the button based on the favourite status
                    if (product.isFavourite()) {
                        favouriteButton.setImageResource(R.drawable.vector); // Change to filled vector drawable
                    } else {
                        favouriteButton.setImageResource(R.drawable.vector_3); // Change to outline vector drawable
                    }

                }
            });
        }
        private String formatPrice(double price) {
            // Nếu giá có phần thập phân
            if (price % 1 == 0) {
                return String.format("%,.0f", price); // Sử dụng định dạng không có phần thập phân
            } else {
                return String.format("%,.2f", price); // Sử dụng định dạng có phần thập phân
            }
        }
        private int getBrandImageId(Context context, String brand) {
            String brandLowercase = brand.toLowerCase();
            String resourceName = "drawable/" + brandLowercase;
            return context.getResources().getIdentifier(resourceName, null, context.getPackageName());
        }
        private double getLowestPrice(Product product) {
            double jbhifiFee = product.getJbhifi_fee();
            double officeworkFee = product.getOfficework_fee();
            double goodguysFee = product.getGoodguys_fee();
            double bigwFee = product.getBigw_fee();
            double brandFee = product.getBrand_fee();

            double lowestPrice = Math.min(Math.min(jbhifiFee, officeworkFee),
                    Math.min(Math.min(goodguysFee, bigwFee), brandFee));
            return lowestPrice;
        }
    }
}
