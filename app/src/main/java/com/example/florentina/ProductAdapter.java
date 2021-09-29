package com.example.florentina;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.product_item,parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.description.setText(product.getDescription());
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductHolder extends  RecyclerView.ViewHolder{

        TextView name, description, price;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productname);
            description = itemView.findViewById(R.id.productdesc);
            price = itemView.findViewById(R.id.productprice);

        }
    }
}
