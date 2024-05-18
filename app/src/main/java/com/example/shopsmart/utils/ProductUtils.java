// ProductUtils.java
package com.example.shopsmart.utils;

import android.content.Context;
import com.example.shopsmart.Domain.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductUtils {

    public static List<Product> loadProductsFromJson(Context context) {
        List<Product> productList = new ArrayList<>();
        String jsonString = loadJsonFromAsset(context, "products.json");

        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Product product = new Product(
                            jsonObject.getString("id"),
                            jsonObject.getString("title"),
                            (float) jsonObject.getDouble("score_rating"),
                            jsonObject.getString("brand"),
                            jsonObject.getString("type"),
                            jsonObject.getDouble("jbhifi_fee"),
                            jsonObject.getDouble("officework_fee"),
                            jsonObject.getDouble("goodguys_fee"),
                            jsonObject.getDouble("bigw_fee"),
                            jsonObject.getDouble("brand_fee"),
                            jsonObject.getString("releaseDate"),
                            jsonObject.getString("jbhifi_link"),
                            jsonObject.getString("goodguys_link"),
                            jsonObject.getString("officework_link"),
                            jsonObject.getString("bigw_link"),
                            jsonObject.getString("brand_link"),
                            jsonObject.getString("description"),
                            jsonObject.getString("specs")
                    );
                    productList.add(product);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return productList;
    }

    private static String loadJsonFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
