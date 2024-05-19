package com.example.shopsmart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;

public class IphoneAdapter extends PagerAdapter {

    private final List<Uri> imageUris;
    private final LayoutInflater layoutInflater;


    public IphoneAdapter(List<Uri> imageUris, Context context) {
        this.imageUris = imageUris;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }

    //creating the views for each page in the ViewPager

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myImageLayout = layoutInflater.inflate(R.layout.iphone_image, container, false);
        ImageView imageView = myImageLayout.findViewById(R.id.imageView62);
        Picasso.get().load(imageUris.get(position)).into(imageView);
        container.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
