package com.example.florentina;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    public static final String TAG = "TAG";

    private Context context;
    private ArrayList<Customer> customerArrayList;
    //private StorageReference storageReference;

    public CustomerAdapter(Context context, ArrayList<Customer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, int position) {
        //View Crud
        Customer cus = customerArrayList.get(position);

        holder.name.setText(cus.getName());
        holder.phone.setText(cus.getPhone());
        holder.email.setText(cus.getEmail());
        holder.address.setText(cus.getAddress());

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SET IMAGE
                // StorageReference profileRef = storageReference.child("creatorpics/"+cus.getCusid()+"/profile.jpg");
                // profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                // @Override
                // public void onSuccess(Uri uri) {
                // Picasso.get().load(uri).into(holder.img);
                //  }
                // });

                DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.customer_popup))
                        .setExpanded(true, 2000)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText username, name, phone, address, email, password;

                username = view.findViewById(R.id.userusername);
                name = view.findViewById(R.id.username);
                phone = view.findViewById(R.id.userphone);
                address = view.findViewById(R.id.useraddress);
                email = view.findViewById(R.id.useremail);
                password = view.findViewById(R.id.userpword);

                Button btnUpdate = view.findViewById(R.id.customerpopup_submitbtn);


                username.setText(cus.getUserName());
                name.setText(cus.getName());
                phone.setText(cus.getPhone());
                address.setText(cus.getAddress());
                email.setText(cus.getEmail());
                password.setText(cus.getPassword());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Address", address.getText().toString());
                        map.put("Email", email.getText().toString());
                        map.put("Name", name.getText().toString());
                        map.put("Password", password.getText().toString());
                        map.put("Phone", phone.getText().toString());
                        map.put("UserName", username.getText().toString());

                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(cus.getCusid());
                        documentReference.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Data updated sucessfully", Toast.LENGTH_SHORT).show();
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
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted users cannot be recovered.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseFirestore.getInstance().collection("users").document(cus.getCusid()).delete();
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


    //////////USed in view crud/////////////////////

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, phone, address;
        Button editbtn, deletebtn;
        CircleImageView img;


        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            editbtn = itemView.findViewById(R.id.useredit);
            deletebtn = itemView.findViewById(R.id.userdelete);
            img = itemView.findViewById(R.id.img4);

            name = itemView.findViewById(R.id.cv_name);
            email = itemView.findViewById(R.id.cv_email);
            phone = itemView.findViewById(R.id.cv_phone);
            address = itemView.findViewById(R.id.cv_address);

            //itemView.setOnClickListener(this);
        }
    }
}
