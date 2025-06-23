package com.example.tranbaongoc_k224111494_m02.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tranbaongoc_k224111494_m02.R;
import com.example.tranbaongoc_k224111494_m02.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    public void setProducts(ArrayList<Product> newProducts) {
        this.products = newProducts;
        clear();
        addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(resource, null);

        TextView txtProductId = item.findViewById(R.id.txtProductId);
        TextView txtProductName = item.findViewById(R.id.txtProductName);
        TextView txtProductPrice = item.findViewById(R.id.txtProductPrice);
        ImageView imgProduct = item.findViewById(R.id.imgProduct);

        Product p = products.get(position);
        txtProductId.setText("ID: " + p.getId());
        txtProductName.setText(p.getProductName());
        txtProductPrice.setText(p.getUnitPrice() + " VND");

        if (p.getImageLink() != null && !p.getImageLink().isEmpty()) {
            Picasso.get()
                    .load(p.getImageLink())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(imgProduct);
        } else {
            imgProduct.setImageResource(R.drawable.ic_placeholder);
        }

        return item;
    }
}
