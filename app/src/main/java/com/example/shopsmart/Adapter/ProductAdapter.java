package com.example.shopsmart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsmart.Domain.Product;
import com.example.shopsmart.IPhonePinkActivity;
import com.example.shopsmart.MyInterestActivity;
import com.example.shopsmart.ProductActivity1;
import com.example.shopsmart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<Product> favoriteProducts;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void setFavoriteProducts(List<Product> products) {
        favoriteProducts = products;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_prod_list, parent, false);
        return new ProductViewHolder(view, favoriteProducts);
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
    public Product getProduct(int position) {
        return productList.get(position);
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private Product product;
        private TextView titleTxt;
        private TextView feeTxt;
        private TextView releaseDateTxt;
        private TextView brandNameTxt;
        private TextView retailersTxt;
        private TextView scoreTxt;
        private ImageView brandImg;
        private ImageView pic;
        private ImageButton favouriteButton;
        private List<Product> favoritesProducts;
        public ProductViewHolder(@NonNull View itemView, @Nullable List<Product> favorites) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            releaseDateTxt = itemView.findViewById(R.id.releaseDateTxt);
            brandNameTxt = itemView.findViewById(R.id.brand_name_txt);
            retailersTxt = itemView.findViewById(R.id.number_retailer);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            brandImg = itemView.findViewById(R.id.brand_img);
            pic = itemView.findViewById(R.id.pic);
            favouriteButton = itemView.findViewById(R.id.favourite_btn);
            favoritesProducts = favorites;
        }

        public void bind(Product product) {
            this.product = product;
            titleTxt.setText(product.getTitle());
            retailersTxt.setText(String.valueOf(product.getNumber_retailers()));
            scoreTxt.setText(String.valueOf(product.getScore_rating()));
            feeTxt.setText(formatPrice(product.getMinFee()));
            releaseDateTxt.setText(product.getReleaseDate());
            brandNameTxt.setText(product.getBrand());
            int brandImageId = getBrandImageId(itemView.getContext(), product.getBrand());
            brandImg.setImageResource(brandImageId);

            if (product.isFavourite()) {
                favouriteButton.setImageResource(R.drawable.vector); // Change to filled vector drawable
            } else {
                favouriteButton.setImageResource(R.drawable.vector_3); // Change to outline vector drawable
            }

            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.setFavourite(!product.isFavourite());
                    if (product.isFavourite()) {
                        favouriteButton.setImageResource(R.drawable.vector);
                        favoritesProducts.add(product);
                    } else {
                        favouriteButton.setImageResource(R.drawable.vector_3);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, IPhonePinkActivity.class);
                    intent.putExtra("productId", product.getId());
                    intent.putExtra("productTitle", product.getTitle());
                    intent.putExtra("productBrand", product.getBrand());
                    intent.putExtra("productType", product.getType());
                    intent.putExtra("productPrice", product.getMinFee());
                    intent.putExtra("productScore", product.getScore_rating());
                    intent.putExtra("numberRetailers", product.getNumber_retailers());
                    intent.putExtra("jbhifiFee", product.getJbhifi_fee());
                    intent.putExtra("officeworkFee", product.getOfficework_fee());
                    intent.putExtra("goodguysFee", product.getGoodguys_fee());
                    intent.putExtra("bigwFee", product.getBigw_fee());
                    intent.putExtra("brandFee", product.getBrand_fee());
                    intent.putExtra("jbhifiLink", product.getJbhifi_link());
                    intent.putExtra("officeworkLink", product.getOfficework_link());
                    intent.putExtra("goodguysLink", product.getGoodguys_link());
                    intent.putExtra("bigwLink", product.getBigw_link());
                    intent.putExtra("brandLink", product.getBrand_link());
                    intent.putExtra("description", product.getDescription());
                    intent.putExtra("specs", product.getSpecs());
                    intent.putExtra("minFee", product.getMinFee());
                    context.startActivity(intent);
                }
            });

            loadFirstImageFromFirebase(product.getImageFolder(), pic);
        }
        public Product getProduct() {
            return product;
        }

        private void loadFirstImageFromFirebase(String folderPath, ImageView imageView) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(folderPath);

            storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    if (!listResult.getItems().isEmpty()) {
                        StorageReference firstImageRef = listResult.getItems().get(0);
                        firstImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(imageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        private String formatPrice(double price) {
            if (price % 1 == 0) {
                return String.format("%,.0f", price);
            } else {
                return String.format("%,.2f", price);
            }
        }

        private int getBrandImageId(Context context, String brand) {
            String brandLowercase = brand.toLowerCase();
            String resourceName = "drawable/" + brandLowercase;
            return context.getResources().getIdentifier(resourceName, null, context.getPackageName());
        }

    }
}
