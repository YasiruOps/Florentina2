 package com.example.florentina;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {


    private Context context;
    private ArrayList<Customer> customerArrayList;

    public CustomerAdapter(Context context, ArrayList<Customer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.customerview, parent, false);
        return new CustomerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        Customer cus = customerArrayList.get(position);

        holder.name.setText(cus.Name);
        holder.phone.setText(cus.Phone);
        holder.email.setText(cus.Email);
        holder.address.setText(cus.Address);

    }



    //////////USed in view crud/////////////////////

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView name, email, phone, address;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.cv_name);
            email= itemView.findViewById(R.id.cv_email);
            phone= itemView.findViewById(R.id.cv_phone);
            address= itemView.findViewById(R.id.cv_address);

            //itemView.setOnClickListener(this);
        }

        //Used in update crud
        @Override
        public void onClick(View v) {

            //gets product position
            Customer customer = customerArrayList.get(getAdapterPosition());

            Intent intent = new Intent(context, UpdateCustomer.class);
            intent.putExtra("customer", customer);
            context.startActivity(intent);
        }
    }


}
