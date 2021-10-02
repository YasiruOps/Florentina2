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

        Glide.with(context)
                .load(product.getImageURL())
                .into(holder.img);

        holder.description.setText(product.getDescription());
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));


        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.products_popup))
                        .setExpanded(true, 1900)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText varname, vardesc, varprice, varquantity, varimgurl;
                varname = view.findViewById(R.id.enterproductname);
                vardesc = view.findViewById(R.id.enterproductdesc);
                varprice = view.findViewById(R.id.enterproductprice);
                varquantity = view.findViewById(R.id.enterproductquantity);
                varimgurl = view.findViewById(R.id.enterproducturl);

                Button btnUpdate = view.findViewById(R.id.updateproductbtn);

                varname.setText(product.getName());
                vardesc.setText(product.getDescription());
                varprice.setText(Float.toString(product.getPrice()));
                varquantity.setText(Integer.toString(product.getQuantity()));
                varimgurl.setText(product.getImageURL());

                dialogPlus.show();

                //UPDATE CRUD
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("description",vardesc.getText().toString());
                        map.put("imageURL", varimgurl.getText().toString());
                        map.put("name", varname.getText().toString());
                        map.put("price",Float.parseFloat(varprice.getText().toString()));
                        map.put("quantity",Integer.parseInt(varquantity.getText().toString()));

                        FirebaseDatabase.getInstance().getReference()
                                .child("Products")
                                .child(product.getProductId())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Updated Sucessfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Error while updating", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });

        //DELETE CRUD
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be undone.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(product.getProductId()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductHolder extends  RecyclerView.ViewHolder{

        TextView name, description, price, quantity;
        ImageView img;

        Button btnedit, btndelete;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            btndelete = itemView.findViewById(R.id.productdeletebtn);
            btnedit = itemView.findViewById(R.id.productupdatebtn);

            img = itemView.findViewById(R.id.img2);

            name = itemView.findViewById(R.id.productname);
            description = itemView.findViewById(R.id.productdesc);
            price = itemView.findViewById(R.id.productprice);
            quantity = itemView.findViewById(R.id.productquantity);

        }
    }
}
