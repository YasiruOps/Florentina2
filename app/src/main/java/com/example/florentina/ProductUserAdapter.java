package com.example.florentina;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductUserAdapter extends RecyclerView.Adapter<ProductUserAdapter.ProductHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    private Integer counter = 0;
    private Float Total;

    public ProductUserAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductUserAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.product_user_item,parent, false);
        return new ProductHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductUserAdapter.ProductHolder holder, int position) {

        Product product = productArrayList.get(position);

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                Total = counter * product.getPrice();
                holder.total.setText(Float.toString(Total));
                holder.pcount.setText(Integer.toString(counter));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                Total = counter * product.getPrice();
                holder.total.setText(Float.toString(Total));
                holder.pcount.setText(Integer.toString(counter));

            }
        });





        Glide.with(context)
                .load(product.getImageURL())
                .into(holder.img);

        holder.description.setText(product.getDescription());
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductHolder extends  RecyclerView.ViewHolder{

        TextView name, description, price, quantity;
        ImageView img, plus, minus;
        TextView pcount, total;



        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            plus = itemView.findViewById(R.id.plus_sign);
            minus = itemView.findViewById(R.id.minus_sign);

            pcount=itemView.findViewById(R.id.product_count);
            total = itemView.findViewById(R.id.totalproducts);

            img = itemView.findViewById(R.id.img2);

            name = itemView.findViewById(R.id.productname);
            description = itemView.findViewById(R.id.productdesc);
            price = itemView.findViewById(R.id.productprice);
            quantity = itemView.findViewById(R.id.productquantity);

        }
    }
}
